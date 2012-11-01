var color = d3.scale.category20b();

function hash(s) {
	if (!s) return 0;
	for(var ret = 0, i = 0, len = s.length; i < len; i++) {
    	ret = (31 * ret + s.charCodeAt(i)) << 0;
  	}
	return ret;
}

var ignore = { source: 1, target:1, type:1, selected:1, index:1, x:1, y:1, weight:1, px:1,py:1, id: 1};
function propertyHash(ob) {
	var ret=0;
	for (var prop in ob) {
		if (ignore.hasOwnProperty(prop)) continue;
		if (ob.hasOwnProperty(prop)) {
			ret += hash(prop);
		}
	}
	return ret;
}

function toString(ob) {
	var ret="";
	for (var prop in ob) {
		if (ignore.hasOwnProperty(prop)) continue;
		if (ob.hasOwnProperty(prop)) {
			ret += prop +": "+ob[prop]+" ";
		}
	}
	return ret+"id: "+ob.id;
}

function title(ob) {
	return ob.title;
}
function name(ob) {
	return ob.name;
}
function visualize(id,w,h,data) {
  var vis = d3.select("#"+id).append("svg")
    .attr("width", w)
    .attr("height", h);

    var force = self.force = d3.layout.force()
        .nodes(data.nodes)
        .links(data.links)
        .gravity(.8)
        .distance(70)
        .charge(-1000)
        .linkDistance(150)
        .charge(-6000)
        .size([w, h])
        .start();


	// end-of-line arrow
		vis.append("svg:defs").selectAll("marker")
		    .data(["end-marker"]) // link types if needed
		    .enter().append("svg:marker")
		    .attr("id", String)
		    .attr("viewBox", "0 -5 10 10")
		    .attr("refX", 25)
		    .attr("refY", -1.5)
		    .attr("markerWidth", 4)
		    .attr("markerHeight", 4)
		    .attr("class","marker")
		    .attr("orient", "auto")
		    .append("svg:path")
		    .attr("d", "M0,-5L10,0L0,5");

    var link = vis.selectAll("line.link")
        .data(data.links)
        .enter().append("svg:line")
        .attr("class", "link")
	//  	.attr("marker-end", function(d) { return "url(#" + "end-marker" + ")"; }) // was d.type //是否是有方向的箭头 
    //    .style("stroke-width", function(d) { return d["selected"] ? 2 : null; })
    	.style("stroke-width",1)
        .style("stroke", function(d){ return d.color; })
        .attr("x1", function(d) { return d.source.x; })
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; });

    var node = vis.selectAll("g.node")
        .data(data.nodes)
//        .enter().append("rect").attr("x",-10).attr("y",-10).attr("width",20).attr("height",20)
        .enter().append("circle")
        .attr("class", "node")
	    .attr("r", 5)
	    .attr("hostname", function(d) { return d.name; })
	    .style("fill", function(d){ return d.fillColor; })
      	.style("stroke-width",5)
//	    .style("border-radius",10).style("-webkit-border-radius",10).style("-moz-border-radius",10)
      	.style("cursor","pointer")
      	.style("stroke", function(d){ return d.fillColor; })
        .call(force.drag);
	  node.append("title").text(function(d) { return d.name; });
	  var text = vis.append("svg:g").selectAll("g")
		    .data(force.nodes())
		    .enter().append("svg:g");
			text.append("svg:text")
		    .attr("x", 8)
		    .attr("y", "-.31em")
		    .attr("class", "text shadow")
		    .text(function(d) { return d.name; }).style("fill", function(d){ return d.fontColor; }).style("font-family", "Arial").style("font-size ", 8);

		text.append("svg:text")
		    .attr("x", 8)
		    .attr("y", "-.31em")
			.attr("class","text")
		    .text(function(d) { return d.name; }).style("fill",  function(d){ return d.fontColor; }).style("font-family", "Arial").style("font-size ", 8);
			var path_text = vis.append("svg:g").selectAll("g")
				    .data(force.links())
				    .enter().append("svg:g").attr("class", "linkNode");
			path_text.append("svg:text")
					.attr("class","path-text shadow")
					//设置连接数目d.type
					.text(function(d) { return "连接数:"+d.type; }).style("fill", function(d){ return d.linkNumberColor; }).style("font-family", "Arial").style("font-size ", 16).style("cursor","pointer");
			path_text.append("svg:text")
					.attr("class","path-text")
					.text(function(d) { return "连接数:"+d.type; }).style("fill", function(d){ return d.linkNumberColor; }).style("font-family", "Arial").style("font-size ", 16).style("cursor","pointer");
    force.on("tick", function() {
      link.attr("x1", function(d) { return d.source.x; })
          .attr("y1", function(d) { return d.source.y; })
          .attr("x2", function(d) { return d.target.x; })
          .attr("y2", function(d) { return d.target.y; });

	  text.attr("transform", function(d) {
		    return "translate(" + d.x + "," + d.y  + ")";
	  });

      node.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });

	  path_text.attr("transform", function(d) {
	    var dx = (d.target.x - d.source.x),
	        dy = (d.target.y - d.source.y);
		var dr = Math.sqrt(dx * dx + dy * dy);
		var sinus = dy/dr;
		var cosinus = dx/dr;
		var l = d.type.length*6;
		var offset = (1 - (l / dr )) / 2;
		var x=(d.source.x + dx*offset);
		var y=(d.source.y + dy*offset);
	    return "translate(" + x + "," + y + ") matrix("+cosinus+", "+sinus+", "+-sinus+", "+cosinus+", 0 , 0)";
	  }).attr("fromhost", function(d) { return d.fromhost; }) .attr("tohost", function(d) { return d.tohost; });

	});
}
function render(id,w,h,url) {
    d3.json(url, function(data) {
        visualize(id,w,h,data);
    });
}