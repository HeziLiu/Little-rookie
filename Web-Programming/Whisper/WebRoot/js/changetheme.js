function change_default(){
		var css=document.getElementById("css");
		var _body=document.getElementById("_body");
		css.href="css/bootstrap.css";
		_body.style.fontFamily="";
		_body.style.fontSize="";
}
function change_white(){
		var css=document.getElementById("css");
		var body=document.getElementById("_body");
		css.href="css/bootstrap1.css";
		body.style.fontFamily="楷体";
		body.style.fontSize="20px";
}
function loop(str){
		var css=document.getElementById("css").href;
		window.location.href=str+"?css="+css;//传递css字体大小和字体参数
}
function getParam(){
		var css=document.getElementById("css");
		var c1=window.location.href.split("?")[1];
		var c2=c1.split("=")[1];
		var _css=css.href.split('/');
		if(css.href!=c2){
			if(_css[_css.length-1]=="bootstrap.css")
				change_white();
			else
				change_default();
		}
}