
var layersGroup = layersString.layers.layer;

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
layersGroup.forEach((layer, i) => {
    const html = '' +
            '<div>' +
            '<label for="' + layer.name + '">' +
            '<input type="checkbox" id="' + layer.name + '" name="' + layer.name + '"/>' +
            '<span>' + layer.name + '</span>' +
            '</label>' +
            '</div>';
    listLayershtml += html;
});
$("#list-couche").html(listLayershtml);

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

$("#updateFilter").click(function () {
    updateFilter();
});

$("#resetFilter").click(function () {
    $("#attrSelect").prop("selectedIndex", 0);
    $("#opSelect").prop("selectedIndex", 0);
    $("#value").val("");
    updateFilter('reset');
});

function updateFilter(type) {
    var cql_filter = {
        'cql_filter': null
    }

    if (type == 'reset') {
        map.getLayers().forEach(layer => {
            layer.getSource().updateParams(cql_filter);
            layer.getSource().refresh();
        });
    } else {
        var attribut = $("#attrSelect").val();
        var operateur = $("#opSelect").val();
        var value = $("#value").val();

        // display value property of select list (from selected option)
        //alert(attribut + '' + operateur + '' + value);
        cql_filter.cql_filter = attribut + '' + operateur + '' + value;
//        alert(JSON.stringify(cql_filter));
        map.getLayers().forEach(layer => {
            layer.getSource().updateParams(cql_filter);
        });
    }



}

