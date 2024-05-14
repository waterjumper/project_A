package com.company.project.service.impl.cooke;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.company.project.common.enums.OrderEnums;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.exception.code.BaseResponseCode;
import com.company.project.common.utils.Constant;
import com.company.project.common.utils.ToolUtils;
import com.company.project.entity.cooke.Cart;
import com.company.project.entity.cooke.Dishes;
import com.company.project.entity.cooke.Order;
import com.company.project.entity.cooke.OrderDetail;
import com.company.project.mapper.cooke.CartMapper;
import com.company.project.mapper.cooke.DishesMapper;
import com.company.project.mapper.cooke.OrderDetailMapper;
import com.company.project.mapper.cooke.OrderMapper;
import com.company.project.pojo.OrderCarItem;
import com.company.project.pojo.OrderCreateReq;
import com.company.project.pojo.OrderCreateResp;
import com.company.project.pojo.OrderQueryReq;
import com.company.project.service.HttpSessionService;
import com.company.project.service.cooke.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private HttpSessionService httpSessionService;

    @Resource
    private CartMapper cartMapper;

    @Resource
    private OrderMapper orderMapper;


    @Resource
    private OrderDetailMapper orderDetailMapper;


    @Resource
    private DishesMapper dishesMapper;


    @Resource
    private TransactionTemplate transactionTemplate;


    @Override
    public OrderCreateResp createOrder(OrderCreateReq vo) {
        Long currentUserId = httpSessionService.getCurrentUserIdAndCheck();
        log.info("createOrder currentUserId:{}", currentUserId);

        List<OrderCarItem> cartList = vo.getCartList();
        if (CollectionUtils.isEmpty(cartList)) {
            throw new BusinessException(BaseResponseCode.ILLEGAL_REQUEST);
        }


        Set<String> productCodes = new HashSet<>();
        Set<Long> cartIds = new HashSet<>();
        cartList.stream().forEach(item -> {
           productCodes.add(item.getProductCode());
            cartIds.add(item.getId());
        });
        if (productCodes.size() != cartList.size()) {
            throw new BusinessException(BaseResponseCode.ILLEGAL_REQUEST);
        }
        if (cartIds.size() != cartList.size()) {
            throw new BusinessException(BaseResponseCode.ILLEGAL_REQUEST);
        }

        // 查询菜品列表
        List<Dishes> dishesList = dishesMapper.selectList(Wrappers.<Dishes>lambdaQuery().in(Dishes::getCode, productCodes));
        if (productCodes.size() != dishesList.size()) {
            throw new BusinessException(BaseResponseCode.ILLEGAL_REQUEST);
        }
        Map<String, Dishes> dishesMap = dishesList.stream().collect(Collectors.toMap(Dishes::getCode, Function.identity()));

        // 查询购物车
        List<Cart> orderDetailList = cartMapper.selectList(Wrappers.<Cart>lambdaQuery().in(Cart::getId, cartIds));
        if (cartIds.size() != orderDetailList.size()) {
            throw new BusinessException(BaseResponseCode.ILLEGAL_REQUEST);
        }


        Order order = new Order();
        order.setOrderStatus(OrderEnums.WAIT_PAY.getValue());
        order.setCode(genCode());
        order.setUserId(currentUserId);

        return transactionTemplate.execute((TransactionStatus status) -> {
            try {
                // 插入订单
                int insertNum = orderMapper.insert(order);
                if (insertNum <= 0) {
                    log.error("createOrder 新增订单错误");
                    throw new BusinessException(BaseResponseCode.ILLEGAL_REQUEST);
                }
                Long orderId = order.getId();


                if (TransactionSynchronizationManager.isSynchronizationActive()) {
                    log.info("createOrder 事务开启，注册事务提交成功后事件");
//                    TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//                        @Override
//                        public void afterCompletion(int status) {
//                            if (TransactionSynchronization.STATUS_COMMITTED == status) {
//                                // 提交事务后出发动作
//                            }
//                        }
//                    });
                }

                // 订单详情
                AtomicInteger totalFee = new AtomicInteger();

                List<OrderDetail> orderDetails = cartList.stream().map(item -> {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderId(orderId);
                    orderDetail.setProductCode(item.getProductCode());
                    orderDetail.setQuantity(item.getQuantity());

                    Dishes dishes = dishesMap.get(item.getProductCode());
                    orderDetail.setUnitPrice(dishes.getPrice());
                    orderDetail.setProductName(dishes.getName());


                    totalFee.addAndGet(item.getQuantity() * dishes.getPrice());
                    return orderDetail;
                }).collect(Collectors.toList());

                // 批量插入
                String sqlStatement = SqlHelper.getSqlStatement(OrderDetailMapper.class, SqlMethod.INSERT_ONE);

                boolean batchInsert = executeBatchCustomer(OrderDetail.class, (sqlSession) -> {
                    int size = orderDetails.size();
                    int i = 1;

                    for (OrderDetail entity : orderDetails) {
                        sqlSession.insert(sqlStatement, entity);

                        if ((i % 10 == 0) || i == size) {
                            sqlSession.flushStatements();
                        }
                        i++;
                    }
                });

                if (!batchInsert) {
                    log.error("createOrder 批量插入订单详情错误");
                    throw new BusinessException(BaseResponseCode.ILLEGAL_REQUEST);
                }

                // 更新订单金额
                Order updateOrder = new Order();
                updateOrder.setId(orderId);
                updateOrder.setTotalAmount(totalFee.get());
                updateOrder.setPaymentAmount(totalFee.get());
                orderMapper.updateById(updateOrder);

                // 删除购物车
                cartMapper.deleteBatchIds(cartIds);


                OrderCreateResp orderCreateResp = new OrderCreateResp();
                orderCreateResp.setOrderCode(order.getCode());
                return orderCreateResp;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        });



    }


    /**
     * 分页查询
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<Order> pageList(OrderQueryReq vo) {
        Page<Order> page = new Page<>(vo.getPage(), vo.getLimit());

        LambdaQueryWrapper<Order> queryWrapper = Wrappers.lambdaQuery();
        if (Objects.nonNull(vo.getUserId())) {
            queryWrapper.eq(Order::getUserId, vo.getUserId());
        }
        if (Objects.nonNull(vo.getStatue())) {
            queryWrapper.eq(Order::getOrderStatus, vo.getStatue());
        }
        if (Objects.nonNull(vo.getCode())) {
            queryWrapper.eq(Order::getCode, vo.getCode());
        }

        queryWrapper.orderByDesc(Order::getId);
        return orderMapper.selectPage(page, queryWrapper);
    }


    private String genCode() {
        String code = ToolUtils.uniCode("o", 6);

        LambdaQueryWrapper<Order> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Order::getCode, code).last(Constant.LIMIT_1);

        Order order = orderMapper.selectOne(queryWrapper);
        while (Objects.nonNull(order)) {
            code = ToolUtils.uniCode("o", 6);
            queryWrapper.eq(Order::getCode, code).last(Constant.LIMIT_1);
            order = orderMapper.selectOne(queryWrapper);
        }

        return code;
    }


    public static SqlSessionFactory sqlSessionFactory(Class<?> clazz) {
        return GlobalConfigUtils.currentSessionFactory(clazz);
    }


    public boolean executeBatchCustomer(Class<?> entityClass, Consumer<SqlSession> consumer) {
        SqlSessionFactory sqlSessionFactory = sqlSessionFactory(entityClass);
        SqlSessionHolder sqlSessionHolder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sqlSessionFactory);


        boolean transaction = TransactionSynchronizationManager.isSynchronizationActive();
        if (sqlSessionHolder != null) {
            SqlSession sqlSession = sqlSessionHolder.getSqlSession();
            //原生无法支持执行器切换，当存在批量操作时，会嵌套两个session的，优先commit上一个session
            //按道理来说，这里的值应该一直为false。
            sqlSession.commit(!transaction);
        }
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        if (!transaction) {
            log.warn("SqlSession [" + sqlSession + "] was not registered for synchronization because DataSource is not transactional");
        }


        try {
            consumer.accept(sqlSession);
            //非事物情况下，强制commit。
            sqlSession.commit(!transaction);

            return true;
        } catch (Throwable t) {
            sqlSession.rollback();
            Throwable unwrapped = ExceptionUtil.unwrapThrowable(t);

            if (unwrapped instanceof RuntimeException) {
                MyBatisExceptionTranslator myBatisExceptionTranslator = new MyBatisExceptionTranslator(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(), true);
                throw Objects.requireNonNull(myBatisExceptionTranslator.translateExceptionIfPossible((RuntimeException) unwrapped));
            }
            throw ExceptionUtils.mpe(unwrapped);
        } finally {
            sqlSession.close();
        }
    }



}
