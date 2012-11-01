//得到浏览器显示的屏幕高度
document.getViewportHeight = function() {
	if (window.innerHeight != window.undefined)
		return window.innerHeight;
	if (document.compatMode == 'CSS1Compat')
		return document.documentElement.clientHeight;
	if (document.body)
		return document.body.clientHeight;
	return window.undefined;
};
// 得到浏览器显示的屏幕宽度
document.getViewportWidth = function() {
	if (window.innerWidth != window.undefined)
		return window.innerWidth;
	if (document.compatMode == 'CSS1Compat')
		return document.documentElement.clientWidth;
	if (document.body)
		return document.body.clientWidth;
};
/**
 * 遮罩层，组件的显示及隐藏
 */
Shade = {
	mask : null,
	container : null,
	isIE6 : null,
	init : function() {
		// 判断浏览器是否是ie6或其以下版本
		var brsVersion = parseInt(window.navigator.appVersion.charAt(0), 10);
		if (brsVersion <= 6 && window.navigator.userAgent.indexOf("MSIE") > -1) {
			this.isIE6 = true;
		} else {
			this.isIE6 = false;
		}
		// 将遮罩层加入body
		var popmask = document.createElement('div');
		popmask.id = 'mask';
		document.body.appendChild(popmask);
		this.mask = document.getElementById("mask");
		this.mask.style.left="0px";
		this.mask.style.top="0px";
		this.mask.style.position="absolute";
		//this.mask.style.zIndex="10000";
		this.mask.style.background="#999";
		this.mask.style.opacity="0.5";
		this.mask.style.filter="alpha(opacity=40)";
		
		//将组件边框加入body
//		var popcont = document.createElement('div');
//		popcont.id = 'popupContainer';
//		popcont.style.position="absolute";
//		popcont.style.zIndex="10001";
//		document.body.appendChild(popcont);
//		this.container = popcont;
	},
	setMaskSize : function() {

		var theBody = document.body;

		var fullHeight = document.getViewportHeight();
		var fullWidth = document.getViewportWidth();

		// Determine what's bigger, scrollHeight or fullHeight / width
		if (fullHeight > theBody.scrollHeight) {
			this.popHeight = fullHeight;
		} else {
			this.popHeight = theBody.scrollHeight;
		}

		if (fullWidth > theBody.scrollWidth) {
			this.popWidth = fullWidth;
		} else {
			this.popWidth = theBody.scrollWidth;
		}

		this.mask.style.height = this.popHeight + "px";
		this.mask.style.width = this.popWidth + "px";
	},
	toCenter : function(conf) {
		var s = this.container.style;
		s.left = (document.getViewportWidth() - conf.width) / 2 + "px";
		s.top = (document.getViewportHeight() - conf.height) / 2 + "px";
	},
	show : function(divId) {
		// 初始化
		this.init();
		// 设置遮罩层的长度和宽度
		this.setMaskSize();
		// 设置组件的标题
		//this.mask.innerHTML = conf.outerHTML;
		// 设置组件的长和宽
//		this.container.style.width = conf.width + "px";
//		this.container.style.height = conf.height + "px";
		//var frame = document.getElementById('popupFrame');
		//frame.style.width = (conf.width - 4) + "px";
		//frame.style.height = (conf.height - 31) + "px";
		// 将组件居中显示
		//this.toCenter(conf);
		// 设置组件内容
		//frame.innerHTML = conf.innerHTML;
		$("#"+divId).show();
	},
	hide : function(divId) {
		$("#"+divId).hide();
		// 删除遮罩层
		document.body.removeChild(this.mask);
		// 删除组件层
		//document.body.removeChild(this.container);
	}
};
function showMask(divId){
	var theBody = document.body;
	var maskDivWidth;
	var maskDivHeight;
	var fullHeight = document.getViewportHeight();
	var fullWidth = document.getViewportWidth();
	if (fullHeight > theBody.scrollHeight) {
		maskDivHeight = fullHeight;
	} else {
		maskDivHeight = theBody.scrollHeight;
	}
	if (fullWidth > theBody.scrollWidth) {
		maskDivWidth = fullWidth;
	} else {
		maskDivWidth = theBody.scrollWidth;
	}
	var obj = document.getElementById(divId);
	var startHtml="<div id='mask' style='left:0px;top:0px;position:absolute;background:#999;opacity: 0.5;display:block;width:"+maskDivWidth+"px;height:"+maskDivHeight+"px'>";
	var endHtml="</div>";
	obj.outerHTML=startHtml+obj.outerHTML+endHtml;
	$("#"+divId).show();
}
function hideMask(divId){
	$("#"+divId).hide();
	document.getElementById("mask").outerHTML=document.getElementById(divId);
}