; (function (window) {
    // https://www.h5pack.com/js-obfuscator
    // 11位手机号码正则
    const REG_PHONE = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;


    if (!window.echoUtil) {
        const echoUtil = {
            jsonToUrlParam: function (json) {
                return new URLSearchParams(json).toString()
            },
            urlParamToJson: function(urlParam) {
                return Object.fromEntries(new URLSearchParams(urlParam || window.location.search))
            },

            isFunction: function (fun) {
                return Object.prototype.toString.call(fun) === '[object Function]'
            }

            , getAppUrl: (path) => {
                return location.origin + path
            }

            , getToken: (key = 'accessToken') => {
                return localStorage.getItem(key)
            }

            , isLogin: (key = 'accessToken') => {
                let token = echoUtil.getToken(key);
                return token && token.length > 0
            }

            , goHome: () => {
                location.href = echoUtil.getAppUrl('/cook')
            }

            , checkLogin: () => {
                let isLogin = echoUtil.isLogin()

                if (!isLogin) {
                    let url = echoUtil.getAppUrl('/cook/login')
                    location.href = url
                    return
                }
            }

            ,getUsername: () => {
                let profile = localStorage.getItem("profile")
                return (profile && JSON.parse(profile).username) || ""
            }

            , json2FormData: (jsonData = {}) => {
                const formData = new FormData()
                Object.keys(obj).map((key) => {
                    formData.append(key, obj[key])
                })
                return formData
            }

            , formData2JSON: (formData) => {
                var jsonObj = {};
                formData.forEach((value, key) => (jsonObj[key] = value));
                return jsonObj
            }

            , isBlank: (val) => {
                return !val || val.trim().length === 0
            }

            , getAccessToken: (name = "access_token") => {
                return localStorage.getItem(name)
            }

            , uniqueId: (prefix = 'u') => {
                return prefix + window.performance.now().toString().replace('.', '') + Math.random().toString(36).substr(2, 9);
            }

            , getCookie: function (cookieName) {
                const strCookie = document.cookie
                const cookieList = strCookie.split(';')

                for (let i = 0; i < cookieList.length; i++) {
                    const arr = cookieList[i].split('=')
                    if (cookieName === arr[0].trim()) {
                        return arr[1]
                    }
                }

                return ''
            }
            , clearAllCookie: function () {
                var keys = document.cookie.match(/[^ =;]+(?=\=)/g)

                if (keys) {
                    for (var i = keys.length; i--;)
                        document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString()
                }
            }
            , logoutAction: () => {
                localStorage.removeItem("accessToken")
                localStorage.removeItem("profile")
                echoUtil.clearAllCookie()

                let url = echoUtil.getAppUrl('/cook/login')
                window.location.href = url
            }
            // 获取当前日期函数
            , getNowFormatDate: (date) => {
                if (!date) {
                    date = new Date()
                }

                return {
                    year: date.getFullYear(), //获取完整的年份(4位)
                    month: date.getMonth() + 1, //获取当前月份(0-11,0代表1月)
                    date: date.getDate(), // 获取当前日(1-31)
                    week: '星期' + '日一二三四五六'.charAt(date.getDay()), //获取当前星期几(0 ~ 6,0代表星期天)
                    hour: date.getHours(), //获取当前小时(0 ~ 23)
                    minute: date.getMinutes(), //获取当前分钟(0 ~ 59)
                    second: date.getSeconds() //获取当前秒数(0 ~ 59)
                }
            }
            // 获取当前日期函数
            , getYYYMMDDDate: (dateObj) => {
                let { year, month, date } = echoUtil.getNowFormatDate(dateObj)
                if (month < 10) month = `0${month}` // 如果月份是个位数，在前面补0
                if (date < 10) date = `0${date}` // 如果日是个位数，在前面补0

                return `${year}-${month}-${date}`
            }

            , getYYYMMDDMMSSDate: (dateObj) => {
                let { year, month, date, hour, minute, second } = echoUtil.getNowFormatDate(dateObj)
                if (month < 10) month = `0${month}` // 如果月份是个位数，在前面补0
                if (date < 10) date = `0${date}` // 如果日是个位数，在前面补0
                if (hour < 10) hour = `0${hour}`
                if (minute < 10) minute = `0${minute}`
                if (second < 10) second = `0${second}`

                return `${year}-${month}-${date} ${hour}:${minute}:${second}`
            }

            , timestamp2Date: (timestamp) => {
                if (!timestamp) {
                    return ''
                }
                return echoUtil.getYYYMMDDMMSSDate(new Date(timestamp))
            }

            , isPhone: (phone) => {
                return REG_PHONE.test(phone)
            }

        }

        window.echoUtil = echoUtil
    }


    if (!window.echoToast) {
        var bgColors = [
            "linear-gradient(to right, #00b09b, #96c93d)",
            "linear-gradient(to right, #ff5f6d, #ffc371)",
        ]

        const echoToast = {
            success: (msg) => {
                // https://github.com/apvarun/toastify-js/blob/master/example/script.js
                Toastify({
                    text: msg || "Success",
                    duration: 2700,
                    // destination: "https://github.com/apvarun/toastify-js",
                    newWindow: true,
                    gravity: "top",
                    position: 'center',
                    style: {
                        //     background: '#0f3443'
                        // background: "linear-gradient(to right, #ff5f6d, #ffc371)",
                        background: bgColors[Math.floor(Math.random() * bgColors.length)],
                    }
                }).showToast()
            }
            , error: (msg) => {
                Toastify({
                    text: msg || "Error",
                    duration: 2700,
                    newWindow: true,
                    gravity: "top",
                    position: 'center',
                    style: {
                        background: bgColors[1],
                    }
                }).showToast()
            }
        }

        window.echoToast = echoToast
    }

    if (!window.completitionApi) {
        const completitionApi = axios.create({
            // baseURL: localStorage.getItem("apiurl")  || VITE_APP_API_URL,
            baseURL: '',
            timeout: 15000,
            withCredentials: false,
        })


        completitionApi.interceptors.request.use(config => {
            let authorization = echoUtil.getAccessToken('accessToken')
            if (authorization) {
                config.headers['authorization'] = authorization
                config.headers['token'] = authorization
            }

            return config
        }, err => {
            toast.error('request timeout')
            return Promise.resolve(err)
        });


        completitionApi.interceptors.response.use(data => {
            console.log('interceptors.response data=', data)
            let { code, msg } = data.data

            if (code === 401001 || code === 401) {
                echoUtil.logoutAction()
                return
            }

            if (0 !== code) {
                msg = msg || "request error"
                echoToast.error(msg)
                return Promise.reject(new Error(msg))
            }

            return data.data;
        }, err => {
            // console.log('interceptors.response err=', err)
            const { message, response } = err

            if (!response) {
                echoToast.error(message)
                return Promise.reject(err);
            }

            if (response.status !== 200) {
                let data = err.response.data
                let msg = (data && data?.msg) || "server error"
                echoToast.error(msg)
            }

            return Promise.reject(err);
        })

        window.completitionApi = completitionApi
    }


    // api接口
    if (!window.echoLogin) {
        const echoLogin = {
            login: async (phone, password) => {
                let response = await window.completitionApi.post(`/user/api/login`, {
                    phone,
                    password,
                })

                return response.data
            }
            , register: async (data = {}) => {
                let response = await window.completitionApi.post(`/user/api/register`, data)
                return response
            }

            , userInfo: async () => {
                let response = await window.completitionApi.get(`/api/user/info`)
                return response.data
            }
        }

        window.echoLogin = echoLogin
    }

    if (!window.echoApi) {
        const echoApi = {
            dishes: async (data = {}) => {
                let response = await window.completitionApi.post(`/sys/dishes/pageList`, data)
                return response
            }
            , addReview: async (data = {}) => {
                let response = await window.completitionApi.post(`/cart/api/addReview`, data)
                return response.data
            }
            , reviewList: async (data = {}) => {
                let response = await window.completitionApi.post(`/cart/api/reviewList`, data)
                return response.data
            }
            , addCart: async (data = {}) => {
                let response = await window.completitionApi.post(`/cart/api/add`, data)
                return response.data
            }
            , cartIncrement: async (data = {}) => {
                let response = await window.completitionApi.post(`/cart/api/adincrementd`, data)
                return response.data
            }
            , cartDecrement: async (data = {}) => {
                let response = await window.completitionApi.post(`/cart/api/decrement`, data)
                return response.data
            }
            , removeCartItem: async (data = {}) => {
                let response = await window.completitionApi.post(`/cart/api/remove`, data)
                return response.data
            }
            , changePassword: async (data = {}) => {
                let response = await window.completitionApi.post(`/user/api/pwd`, data)
                return response.data
            }
            , logout: async (data = {}) => {
                let response = await window.completitionApi.post(`/user/api/logout`, data)
                return response.data
            }
            , cartList: async (data = {}) => {
                let response = await window.completitionApi.post(`/cart/api/list`, data)
                return response.data
            }

            , createOrder: async (data = {}) => {
                let response = await window.completitionApi.post(`/order/api/create`, data)
                return response.data
            }

            , orderList: async (data = {}) => {
                let response = await window.completitionApi.post(`/order/api/list`, data)
                return response.data
            }
        }

        window.echoApi = echoApi
    }


})(window)