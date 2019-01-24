
var layersGroup = layersString.layers.layer;
var attr = JSON.parse(JSON.stringify(attributesString));
var attributsList = [];
const excludeAttribute = ["gid", "osm_id", "osm_way_id", "ref_cog", "geom"];
const operateurs = ["=", "<>", ">", ">=", "<", "<=", "LIKE", "IN"];

//get All layers attributes
attr.forEach(obj => {
    attrs = [];
    try{
        obj.featureType.attributes.attribute.forEach(attribute => {
            if(excludeAttribute.indexOf(attribute.name) == -1){
                attrs.push(attribute.name);
            }
        });
    }catch(e){
        attrs.push(obj.featureType.name);
    }
//    alert(JSON.stringify(obj.featureType.attributes.attribute));
//    obj.featureType.attributes.attribute.forEach(attribute => {
//        if(excludeAttribute.indexOf(attribute.name) == -1){
//            attrs.push(attribute.name);
//        }
//    });
    attributsList.push(attrs);
});

window.onload = function () {
    var x = document.getElementsByClassName("ol-has-tooltip");
    x[x.length - 1].click();
}

var zoomToExtentControl = new ol.control.ZoomToExtent({
    extent: [
        8.46020030975342, 1.59752249717712,
        16.2306156158447, 13.1404762268066
    ]
});

var layers;
layers = layersGroup.map(layer => {
    return new ol.layer.Tile({
        opacity: 1.0,
        source: new ol.source.TileWMS({
            url: 'http://localhost:8080/geoserver/cameroon/wms',
            params: {
                LAYERS: 'cameroon:' + layer.name,
                TILED: true,
            },
            serverType: 'geoserver'
        })
    });
});

var listLayershtml = "";
var listLayersOptions = "";
layersGroup.forEach((layer, i) => {

    const html = '' +
            '<div>' +
            '<label for="' + layer.name + '">' +
            '<input type="checkbox" id="' + layer.name + '" name="' + layer.name + '"/>' +
            '<span>' + layer.name + '</span>' +
            '</label>' +
            '</div>';

    const option = '<option value="' + layer.name + '">' + layer.name + '</option>';

    listLayersOptions += option;
    listLayershtml += html;
});
$("#list-couche").html(listLayershtml);
$("#listCoucheSel").html(listLayersOptions);

var view = new ol.View({
    center: ol.proj.transform([12.3446, 7.3696], 'EPSG:4326', 'EPSG:3857'),
});

var map = new ol.Map({
    layers: layers,
    logo: false,
    target: 'map',
    view: view,
});

// Loop from the end to get the top layer in first
for (var i = 0; i < layers.length; i++) {
    var visible = new ol.dom.Input(document.getElementById(layersGroup[i].name));
    visible.bindTo('checked', layers[i], 'visible');
}

map.addControl(zoomToExtentControl);
var controls = map.getControls();
var attributionControl;
controls.forEach(function (el) {
    if (el instanceof ol.control.Attribution) {
        attributionControl = el;
    }
});
map.removeControl(attributionControl);

var layerIndex = -1;
$("#listCoucheSel").change(function () {
    let select = $("#listCoucheSel").prop('selectedIndex');
    let selectAttrs = attributsList[select];
    let attrSelect = "";
    selectAttrs.forEach(att =>{
       const option = '<option value="' + att + '">' + att + '</option>';
       attrSelect+=option;
    });
    $("#listAttributSel").html(attrSelect);
    layerIndex = select;
});

$("#updateFilter").click(function () {
    updateFilter();
});

$("#resetFilter").click(function () {
    $("#cqlFilter").val("");
    updateFilter('reset');
});

function updateFilter(type) {
    var cql_filter = {
        'cql_filter': null
    }

    if (type == 'reset') {
        map.getLayers().forEach((layer, i) => {
            if(i == layerIndex){
                layer.getSource().updateParams(cql_filter);
                layer.getSource().refresh();
            }
        });
    } else {
        if($("#cqlFilter").val()){
            cql_filter.cql_filter = $("#cqlFilter").val(); 
            map.getLayers().forEach((layer, i) => {
                if(i == layerIndex){
                    layer.getSource().updateParams(cql_filter);
                }
            });
        }
    }



}

