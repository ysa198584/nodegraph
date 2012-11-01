function amSerialChart(chart,fromData,chartData,chartDiv){
	var valueDataArray=fromData["data"];
	var dateType=fromData["datetype"];
	var unitType=fromData["unittype"];
	var isCritical=fromData["critical"];
	var maxValue=fromData["maxvalue"]; 
	
	
	var dateSetObject=getDateFormat(dateType);
	var unit=getLastUnit(unitType,isCritical);
	
	var lastValueArray=createData(valueDataArray,isCritical,chartData,unitType);
	chartOptionsSet(chart,lastValueArray,dateSetObject,unit,valueDataArray,chartDiv,maxValue);
}
function getLastUnit(unitType,isCritical){
	var unitArray=unitTypeConstant[unitType].split(",");
	if(isCritical){
		return unitArray[1];	
	}
	else{
		return unitArray[0];
	}
}
function getDateFormat(dateType){
	var array=dateFormatMappingConstant[dateType].split(",");
	var dateSetObject={
		categoryAxis:array[0],
		categoryBalloon:array[1]		
	};
	return dateSetObject;
}
function createData(tempData,isCritical,chartData,unitType){
	chartData=[];
	var multiple=1;
	if(isCritical){
		multiple=1/multipleConstant[unitType];
	}
	for(var i=0;i<tempData[0]["datapoints"].length;i++){
			var objValue=tempData[0]["datapoints"];
			var tempDate=new Date(objValue[i][1]*1000);
			var obj={
				dateTime:tempDate
			};
			for(var j=0;j<tempData.length;j++){
				var lineValueData=tempData[j];				
				obj[lineValueData["linetitle"]]=lineValueData["linetitle"];
				var valueArray=lineValueData["datapoints"];
				if(valueArray[i][0]!== null){
					obj[lineValueData["linetitle"]+"Value"]=valueArray[i][0]*multiple;
				}
			}
	 		chartData.push(obj); 	
	}
	return chartData;  
}
function chartOptionsSet(chart,chartData,dateSetObject,unit,dataAll,chartDiv,maxValue){
		
        chart.dataProvider = chartData;
        chart.categoryField = "dateTime";
		chart.pathToImages = "/assets/amcharts/";
        
		chart.removeValueAxesAndGraphs();
        var categoryAxis = chart.categoryAxis;
        categoryAxis.parseDates = true;
        categoryAxis.equalSpacing = true;
        categoryAxis.minPeriod = dateSetObject.categoryAxis; 
        categoryAxis.labelRotation = 90;
        
		var valueAxis = new AmCharts.ValueAxis();
        valueAxis.unit=unit; 
      	valueAxis.dashLength = 1;
        valueAxis.axisColor = "#DADADA";
		if (maxValue != null) {
			valueAxis.maximum=maxValue; 
		}
        valueAxis.logarithmic = false; 					
        chart.addValueAxis(valueAxis);		

		
        for(var i=0;i<dataAll.length;i++){
        	var lineValue= dataAll[i];
        	var graph = new AmCharts.AmGraph();
        	graph.type = "line";
	        graph.lineAlpha = 1;
	        graph.fillAlphas = 0;
	        graph.lineThickness = 2;
	        graph.title = lineValue["linetitle"]; 
        	graph.valueField = lineValue["linetitle"]+"Value";
        	chart.addGraph(graph);
        }

        chartCursor = new AmCharts.ChartCursor();
        chartCursor.cursorPosition = "mouse"; 
        chartCursor.categoryBalloonColor = "#869C71";
        chartCursor.categoryBalloonDateFormat =dateSetObject.categoryBalloon;
        chartCursor.cursorColor = "#869C71";
        chart.addChartCursor(chartCursor); 
        
        var legend = new AmCharts.AmLegend();
        legend.align = "center";
        legend.valueText = "[[description]]";
        chart.addLegend(legend);
		$("#"+chartDiv).html("");
		chart.write(chartDiv);
		
}