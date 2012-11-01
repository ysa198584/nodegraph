(function($) {
	$.incrementedShow = function(option){
		$.extend($.incrementedShow.setting, option);

		$.incrementedShow.template = initTemplate();

		$.incrementedShow.setting.triggerIncrementedElement.click(function(){
			loadData();
		});
		if($.incrementedShow.setting.isInitLoadData){
			loadData();
		}
	}

	$.extend($.incrementedShow,{
		setting: {
			isInitLoadData				:true,
			loadDataURL					:null,
			totalSize					:0,
			incrementedInit				:1,
			incrementedSize				:5,
			triggerIncrementedElement	:null,
			incrementedShowElement		:null,
			templateScriptID			:null,
			template					:null,
			showCallback				:null
		}
	});

	function loadData(){
		var ajaxData = {};
		ajaxData["pageNo"] = $.incrementedShow.setting.incrementedInit;
		ajaxData["pageSize"] = $.incrementedShow.setting.incrementedSize;
		$.ajax({
			type	: "POST",
			dataType: "json",
			url		: $.incrementedShow.setting.loadDataURL,
			data	: ajaxData,
			success: function(result){
				$.incrementedShow.setting.totalSize = result.returnValue.count;
				var data = result.returnValue;
				show(data);
				if($.incrementedShow.setting.incrementedInit++ * $.incrementedShow.setting.incrementedSize >= $.incrementedShow.setting.totalSize){
					$.incrementedShow.setting.triggerIncrementedElement.hide();
				}else{
					$.incrementedShow.setting.triggerIncrementedElement.show();
				}

				if ($.isFunction($.incrementedShow.setting.showCallback)) {
					$.incrementedShow.setting.showCallback.call($);
				}
			}
		});
	}

	function initTemplate(){
		var template = $('<span></span>').template('#' + $.incrementedShow.setting.templateScriptID);
		template.getElements = function(data){
			return this.applyData(data).children();
		};
		return template;
	}

	function show(data){
		var incrementedElements = $.incrementedShow.template.getElements(data);
		$.incrementedShow.setting.incrementedShowElement.append(incrementedElements);
	}
})(jQuery);