/*
$Id: ajax.js 5008 2008-05-15 03:37:52Z xkxmud $
*/
//var 100 = 100; //初始的请求已经接受，客户应当继续发送请求的其余部分。
//var 101 = 101; //服务器将遵从客户的请求转换到另外一种协议。
//var 200 = 200; //一切正常，对get和post请求的应答文档跟在后面。
//var 201 = 201; //服务器已经创建了文档，location头给出了它的url。
//var 202 = 202; //已经接受请求，但处理尚未完成。
//var 203 = 203; //文档已经正常地返回，但一些应答头可能不正确，因为使用的是文档的拷贝。
//var 204 = 204; //没有新文档，浏览器应该继续显示原来的文档。如果用户定期地刷新页面，而servlet可以确定用户文档足够新，这个状态代码是很有用的。
/*
没有全部定义完整，只定义了使用中的
*/
var XMLHTTP_READYSTATE_UNLOAD = 0; //未初始化
var XMLHTTP_READYSTATE_READING = 1; //
var XMLHTTP_READYSTATE_READED = 2; //
var XMLHTTP_READYSTATE_EXCHANING = 3; //
var XMLHTTP_READYSTATE_FINISHED = 4; //

var ajaxarr = new Array();//note 每个URL只允许生成一个AJAX对象，防止重复点击。
var ajaxstackarr = new Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);//note ajax 对象时间占位堆栈。预置位10个，应该足够用了。
var attackevasive = 1;//note 防止刷新是否打开
var ajsetarr = new Array();
var xctrlid;

//构造AJAX类
function ajax_cls(recvtype) {

	for(var _stackindex = 0; _stackindex < ajaxstackarr.length && ajaxstackarr[_stackindex] != 0; _stackindex++);//note 查询空闲的时间位
	ajaxstackarr[_stackindex] = 1;//note 表示该任务占位

	var aj = new Object();

	aj._recvtype = recvtype ? recvtype : 'XML';

	aj._resulthandle = null;
	aj._sendstring = '';
	aj.targeturl = '';
	aj._stackindex = 0;
	aj._stackindex = _stackindex;

	//note 默认为 XML 方式
	aj.setrecvtype = function(recvtype) {
		aj._recvtype = recvtype;
	}

	aj._create = function() {
		var request = false;
		if(window.XMLHttpRequest) {
			request = new XMLHttpRequest();
			if(request.overrideMimeType) {
				request.overrideMimeType('text/xml');
			}
		} else if(window.ActiveXObject) {
			var versions = ['Microsoft.XMLHTTP', 'MSXML.XMLHTTP', 'Microsoft.XMLHTTP', 'Msxml2.XMLHTTP.7.0', 'Msxml2.XMLHTTP.6.0', 'Msxml2.XMLHTTP.5.0', 'Msxml2.XMLHTTP.4.0', 'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP'];
			for(var i=0; i<versions.length; i++) {
				try {
					request = new ActiveXObject(versions[i]);
					if(request) {
						return request;
					}
				} catch(e) {
					continue;
				}
			}
		}
		return request;
	}

	aj._xmlhttp_request = aj._create();


	//note private
	aj._processhandle = function() {
		var XMLHTTP_READYSTATE_FINISHED = 4;
		if(aj._xmlhttp_request.readyState == XMLHTTP_READYSTATE_FINISHED && aj._xmlhttp_request.status == 200) {
			if(ajaxarr.length > 0 ) {
				for(k in ajaxarr) {
					if(ajaxarr[k] == aj.targeturl) {
						ajaxarr[k] = null;
					}
				}
			}

			if(aj._recvtype == 'HTML') {
				aj._resulthandle(aj._xmlhttp_request.responseText, aj);
			} else if(aj._recvtype == 'XML') {
				if(aj._xmlhttp_request.responseXML.lastChild != null) {
					aj._resulthandle(aj._xmlhttp_request.responseXML.lastChild.firstChild.nodeValue, aj);
				}else{
					aj._resulthandle(aj._xmlhttp_request.responseText, aj);
				}
			}
			ajaxstackarr[aj._stackindex] = 0;//note 清空标志位，使其它对象有时间段可以被申请
		}
	}

	//note public
	aj.get = function(targeturl, resulthandle) {
		
		if(in_array(targeturl, ajaxarr)) {
			return false;
		} else {
			ajaxarr.push(targeturl);
		}
		
		aj.targeturl = targeturl;
		aj._xmlhttp_request.onreadystatechange = aj._processhandle;
		aj._resulthandle = resulthandle;
		var delay = attackevasive & 1 ? (aj._stackindex + 1) * 1001 : 100;

		if(window.XMLHttpRequest) {
			setTimeout(function(){
			aj._xmlhttp_request.open('GET', aj.targeturl);
			aj._xmlhttp_request.send(null);}, delay);
		} else {
			setTimeout(function(){
			aj._xmlhttp_request.open("GET", aj.targeturl, true);
			aj._xmlhttp_request.send();}, delay);
		}
	}
	//note public
	aj.post = function(targeturl, sendstring, _resulthandle) {
		/*
		if(in_array(targeturl, ajaxarr)) {
			return false;
		} else {
			ajaxarr.push(targeturl);
		}
		*/
		aj.targeturl = targeturl + '&inajax=1';
		aj._sendstring = sendstring;
		aj._xmlhttp_request.onreadystatechange = aj._processhandle;
		aj._resulthandle = _resulthandle;
		aj._xmlhttp_request.open('POST', aj.targeturl);
		aj._xmlhttp_request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		aj._xmlhttp_request.send(aj._sendstring);
	}
	return aj;
}
//执行JS脚本
var evalscripts = new Array();
function _evalscript(s) {
	if(s.indexOf('<script') == -1) return s;
	var p = /<script[^\>]*?src=\"([^\>]*?)\"[^\>]*?(reload=\"1\")?(?:charset=\"([\w\-]+?)\")?><\/script>/ig;
	var arr = new Array();
	while(arr = p.exec(s)) _appendscript(arr[1], '', arr[2], arr[3]);
	p = /<script (?!src)[^\>]*?( reload=\"1\")?>([^\x00]+?)<\/script>/ig;
	while(arr = p.exec(s)) _appendscript('', arr[2], arr[1]);
	return s;
}
// 绑定JS脚本
function _appendscript(src, text, reload, charset) {
	var id = hash(src + text);
	if(!reload && in_array(id, evalscripts)) return;
	if(reload && $(id)) {
		$(id).parentNode.removeChild($(id));
	}

	evalscripts.push(id);
	var scriptNode = document.createElement("script");
	scriptNode.type = "text/javascript";
	scriptNode.id = id;
	scriptNode.charset = charset;
	try {
		if(src) {
			scriptNode.src = src;
		} else if(text){
			scriptNode.text = text;
		}
		$('supevbox').appendChild(scriptNode);
	} catch(e) {}
}
//对JS进行过滤
function _stripscript(s) {
	s = unescape(s);
	return s.replace(/<script.*?>.*?<\/script>/ig, '');
}

/*	调用远程接口，并返回UI套入到当前div的容器中。
 *@ url: 需求请求的 url
 *@ id : 显示的 id
 *@ _waiting: 等待的 id，默认为显示的 id，如果 _waiting 为空字符串，则不显示 loading...， 如果为 null，则在 showid 区域显示
 *@ linkid: 是哪个链接触发的该 ajax 请求，该对象的属性(如 ajaxdisplay)保存了一些 ajax 请求过程需要的数据。
 *@ recall:　回调函数
*/
function aj_load(url, oshowid, force, loading) {

	force = empty(force) ? false : force;
	if(force == false && in_array(url, ajsetarr)) {
		return false;
	} else {
		ajsetarr.push(url);
	}

	var x = new ajax_cls();
	var oshow = $(oshowid);
	var autogoto = 0;

	loading = empty(loading) ? 'loading...' : loading;
	oshow.innerHTML = '<p class="info"><img src="'+jsenv['THEME_PATH']+'res/loading.gif"> ' + loading + '</p>';

	x.oshow = oshow;

	if(url.substr(strlen(url) - 1) == '#') {
		url = url.substr(0, strlen(url) - 1);
		autogoto = 1;
	}

	var url = url + '&inajax=1&ajaxtarget=' + oshowid;

	x.get(url, function(s, x) {
		if(_ajaxshow(s)) {
			return;
		}
		_evalscript(s);
		s = _stripscript(s);
		_sinnerHTML(x.oshow, s);
		_ajaxupdateevents(x.oshow);
		if(autogoto) {
			scroll(0, x.oshow.offsetTop);
		}
	});
}
/*	调用远程接口，并返回UI套入到当前容器中。
 *@ url: 需求请求的 url
 *@ id : 显示的 id
*/
function aj_getdirname(url, oshowid) {
	var x = new ajax_cls();
	var oshow = $(oshowid);
	
	x.oshow = oshow;
	var url = url + '&inajax=1&ajaxtarget=' + oshowid;	
	x.get(url, function(s, x) {
		_ssetvalue(x.oshow, s);
	});
}
// 将返回信息插入到容器的value值
function _ssetvalue(oshow, s) {
	oshow.value = s;
}
//仅调用远程接口，不返回任何UI
function aj_send(url) {
	var x = new ajax_cls();
	var url = url + '&inajax=1';
	x.get(url, function(s, x){
		if(_ajaxshow(s)) {
			return;
		}
		_evalscript(s);
		s = _stripscript(s);
	});
}

var checkedtab = 'purple';
var uncheckedtab = 'sep';
/**
* 设置Tab.  调用远程UI，并在当前窗口中切换
*/
function aj_settab(cur, tabs_label, e) {
	if(isset(e)) {
		doane(e);
	}

	ocur = $(cur);
	ocur_btn = $(cur+'_btn');

	eval('var tabs='+tabs_label);

	var l = tabs.length;

	for(i = 0;  i < l; i++) {
		$(tabs[i]+'_btn').className = uncheckedtab;
		$(tabs[i]).style.display = 'none';
	}

	ocur.style.display = '';
	ocur_btn.className = checkedtab;
	ocur_btn.blur();

	if(!empty(ocur_btn.href)) {
		aj_load(ocur_btn.href, cur);
	}
}

function aj_form(e, ctrlid, divclass, lefttime) {
	divclass = empty(divclass) ? 'ajax_form' : divclass;
	lefttime = empty(lefttime) ? 999999 : lefttime;
	xctrlid = ctrlid;
	aj_menu(e, ctrlid, divclass, lefttime);
}

function aj_menu(e, ctrlid, divclass, lefttime) {

	divclass = empty(divclass) ? 'ajax_msg' : divclass;
	lefttime = empty(lefttime) ? 2 : lefttime;

	var div = $(ctrlid + '_menu');

	if(empty(div)) {
		div = $c('div');
		div.id = ctrlid + '_menu';
		div.style.display = 'none';
		$('supevbox').appendChild(div);
	}

	div.className = divclass;
	var x = new ajax_cls();
	x.ctrlid = ctrlid;
	var href = isset($(x.ctrlid).href) ? $(x.ctrlid).href : $(x.ctrlid).attributes['href'].value;

	x.div = div;
	x.get(href + '&inajax=1&ajaxtarget='+x.ctrlid+'_menu',function(s, x) {
		_evalscript(s);
		s = _stripscript(s);
		if(x.div) {
			x.div.innerHTML = s;
		}
		showMenu(x.ctrlid, true, 0, lefttime);

	});
	doane(e);
}
function myaj_menu(e, ctrlid, divclass, lefttime) {

	
	var x = new ajax_cls();
	x.ctrlid = ctrlid;
	
	var href = isset($(x.ctrlid).href) ? $(x.ctrlid).href : $(x.ctrlid).attributes['href'].value;
	
	x.get(href + '&inajax=1&ajaxtarget='+x.ctrlid+'_menu',function(s, x) {
		_evalscript(s);
		s = _stripscript(s);
		
		alert(s);
		

	});
	doane(e);
}
// 将返回信息插入到容器
function _sinnerHTML(oshow, s) {
	if(oshow.tagName != 'TBODY') {
		oshow.innerHTML = s;
	} else {
		while(oshow.firstChild) {
			oshow.firstChild.parentNode.removeChild(oshow.firstChild);
		}
		var div1 = $c('DIV');
		div1.id = oshow.id+'_div';
		div1.innerHTML = '<table><tbody id="'+oshow.id+'_tbody">'+s+'</tbody></table>';
		$('supevbox').appendChild(div1);
		var trs = div1.getElementsByTagName('TR');
		var l = trs.length;
		for(var i=0; i<l; i++) {
			oshow.appendChild(trs[0]);
		}
		var inputs = div1.getElementsByTagName('INPUT');
		var l = inputs.length;
		for(var i=0; i<l; i++) {
			oshow.appendChild(inputs[0]);
		}
		div1.parentNode.removeChild(div1);
	}
}

//附加事件
function _ajaxupdateevents(obj, tagName) {
	tagName = tagName ? tagName : 'A';
	var objs = obj.getElementsByTagName(tagName);
	for(k in objs) {
		var o = objs[k];
		_ajaxupdateevent(o);
	}
}
//更新事件
function _ajaxupdateevent(o) {
	if(typeof o == 'object' && o.getAttribute) {
		if(o.getAttribute('ajaxtarget')) {
			if(!o.id) {
				o.id = ('id' + Math.random() * 10).replace(/\./i, '');	//note 该标签唯一标识
			}
			var ajaxevent = o.getAttribute('ajaxevent') ? o.getAttribute('ajaxevent') : 'click';//note 默认为 click 事件
			var ajaxurl = o.getAttribute('ajaxurl') ? o.getAttribute('ajaxurl') : o.href;//note 默认为 click 事件
			_attachEvent(o, ajaxevent, newfunction('aj_load', ajaxurl, o.getAttribute('ajaxtarget'), o.getAttribute('ajaxwaitid'), o.getAttribute('ajaxloading'), o.getAttribute('ajaxdisplay')));
			if(o.getAttribute('ajaxfunc')) {
				o.getAttribute('ajaxfunc').match(/(\w+)\((.+?)\)/);
				_attachEvent(o, ajaxevent, newfunction(RegExp.$1, RegExp.$2));
			}
		}
	}
}
//弹出js信息
function _ajaxshow(s) {
	if(s.indexOf('ajaxshow()') != -1) {
		//ajaxshow();
		return true;
	}
	return false;
}


//展示列表，含翻页等功能
function getform(formName) {
	returnString = "";
	formElements = $(formName).elements;

	var first = true;

	for (var i=0; i< formElements.length; i++) {

		var e = formElements[i];
		if(e.name == null || e.name==""){
			continue;
		}
		if(e.type == "radio"){
			if(e.checked){//判断单选按钮是否被选中
				if(first == true){
					first = false;
					returnString += escape(e.name) + "=" + escape(e.value);
				}else{
					returnString += "&" + escape(e.name) + "=" + escape(e.value);
				}
			}
		}else{
			if(first == true){
				first = false;
				returnString += escape(e.name) + "=" + escape(e.value);
			}else{
				returnString += "&" + escape(e.name) + "=" + escape(e.value);
			}
		}
	}
	return returnString;
}
//提交一个post请求，并把返回结果在当前容器中
function aj_submit(formid, outid, insert, except, loading) {
	var x = new ajax_cls();
	var param = getform(formid) + '&inajax=1';
	insert = empty(insert) ? 0 : insert;
	except = empty(except) ? '' : except;
	loading = empty(loading) ? false : true;
	loadingstr = '';
	x.outid = outid;
	if(loading == true) {
		loadingstr = "<p class='info'><img src='"+jsenv['THEME_PATH']+"res/loading.gif'>loading...</p>";
	}

	var tmpinnerHTML = $(x.outid).innerHTML;
	if(isset(except)) {
		if(tmpinnerHTML.indexOf(except) != -1) {
			tmpinnerHTML = '';
		}
	}
	if(loading == true) {
		if(insert != 0) {
			$(x.outid).innerHTML = loadingstr + tmpinnerHTML;
		} else {
			$(x.outid).innerHTML = loadingstr;
		}
	}
	x.post($(formid).action, param, function(s, x) {

			setMenuPosition($(x.outid).ctrlid, 0);
			setTimeout("hideMenu()", 3000);
			_evalscript(s);
			s = _stripscript(s);
			if(insert != 0) {
				$(x.outid).innerHTML = s + tmpinnerHTML;
			} else {
				$(x.outid).innerHTML = s;
			}
			_ajaxupdateevents($(x.outid));

	});

	return false;
}