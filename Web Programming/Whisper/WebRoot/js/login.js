function login(){//登陆
	var pars="u="+$("username").value+"&p="+$("password").value;
	var myAjax=new Ajax.Request("login_submit.jsp",{method:"get",parameters:pars,onComplete:callLogin});
}

function callLogin(r){//回调函数
	var t=r.responseText.trim();
	var o=$("acc");
	if(t=="ac"){
		o.innerHTML="登陆成功，三秒后自动跳转到：<a href='main.html'>我的大厅</a>";
		setTimeout("window.location='main.html'",3000);
	}else{
		o.innerHTML=t;
	}
}

String.prototype.trim=function(){
	return this.replace(/(^\s*)|(\s*$)/g, '');
}