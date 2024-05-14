describe('Login Test', () => {
  it('Visits the login page and logs in', () => {
    cy.visit('http://10.8.89.240:8080/cook/login'); // 访问登录页面
    // 输入手机号
    cy.get('input[name="phone"]').type('13952110111');

    // 输入密码
    cy.get('input[name="password"]').type('123');

    // 点击登录按钮
    cy.get('button[type="submit"]').click();

    // 可选: 检查是否跳转到了新的页面或出现了某些期望的元素
     cy.url().should('include', '/home'); // 假设登录后跳转到home页面
  });


});