var URL = 'https://free-api.heweather.com/s6/weather/now?location=天津&key=e13d97893ef041e58fc1735509a61961';

function getWeather() {
		$.ajax({
			type : "get",
			url : URL,
			dataType : "json",
			success : function(data) {
				console.log(data)
			},
			complete : function(XMLHttpRequest, textStatus) {
			},
			error : function(message) {
				console.log("调用ajax请求出错");
				console.log(message.status)
			}
		})
	}
	
	getWeather();
	
	//IE内核适应性没有处理,会出问题
	var httpRequest = new XMLHttpRequest();//第一步：建立所需的对象
	httpRequest.open('GET', URL, true);//第二步：打开连接  将请求参数写在url中 
	httpRequest.send();//第三步：发送请求  将请求参数写在URL中
	/**
	 * 获取数据后的处理程序
	 */
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState == 4 && httpRequest.status == 200) {
			var json = httpRequest.responseText;//获取到json字符串，还需解析
			console.log(json)
		}
	};