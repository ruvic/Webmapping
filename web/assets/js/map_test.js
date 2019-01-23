
var layersGroup = layersString.layers.layer;
        
window.onload = function(){
    var x = document.getElementsByClassName("ol-has-tooltip");
    x[x.length-1].click();
}

var zoomToExtentControl = new ol.control.ZoomToExtent({
    extent : [
          8.46020030975342, 1.59752249717712, 
          16.2306156158447, 13.1404762268066 
        ]
  });
  
var layers;
layers = layersGroup.map(layer =>{
    return new ol.layer.Tile({
      opacity : 1.0,
      source : new ol.source.TileWMS({
         url : 'http://localhost:8080/geoserver/cameroon/wms',
         params: {
             LAYERS : 'cameroon:'+layer.name,
             TILED : true,
         },
         serverType : 'geoserver'
      })
   }); 
});

  var view = new ol.View({
    center: ol.proj.transform([12.3446, 7.3696], 'EPSG:4326', 'EPSG:3857'),
  });

  var map = new ol.Map({
    layers: layers,
    logo : false,
    target: 'map',
    view: view,
  });

  // Loop from the end to get the top layer in first
  for (var i = 0; i < layers.length; i++) {
    var visible = new ol.dom.Input(document.getElementById('layer_' + i));
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

  map.on('singleclick', function(evt) {
    document.getElementById('info').innerHTML = '';
    var viewResolution = /** @type {number} */ (view.getResolution());
    var url = wmsSource.getGetFeatureInfoUrl(
      evt.coordinate, viewResolution, 'EPSG:3857',
      {
        'INFO_FORMAT': 'application/json'
        // 'format_options': 'callback:parseResponse',
        // Définition des champs pour lesquels on veut obtenir les valeurs attributaires
        // 'propertyName': 'LANAME,LAND,CFCC',
      });
    if (url) {
      console.log(url);
      document.getElementById('info').innerHTML =
          '<iframe seamless src="' + url + '"></iframe>';
    }
    //   $.getJSON(url, function (json) {
    //     // Déclaration de l'objet géographique retourné, ici il n'y en a qu'un pour cette couche
    //     // var feature = json.features[0];
    //     // Déclaration de la valeur de l'attribut affecté à l'objet
    //     // var laname = feature.properties.LANAME;
    //     // console.log('nom : ', laname);
    //     console.log(json);
    //   }); 

  });

//   map.on('singleclick', function(evt, data) {
//     console.log(evt);
//     console.log(data);
//     document.getElementById('info').innerHTML = '';
//     var viewResolution = /** @type {number} */ (view.getResolution());
//     var url = wmsSource.getGetFeatureInfoUrl(
//       evt.coordinate, viewResolution, 'EPSG:3857',
//       {
//         'INFO_FORMAT': 'application/json'
//       }
//     );

//     $.getJSON(url, function (json) {
//       console.log(json);
//     }); 

//   });

  $("#dataForm").submit(function(e){
    var formData = new FormData(this);
    $.ajax({
        url: "http://localhost:8080/geoserver/rest/workspaces/cameroon/datastores/cameroon_map/external.shp",
        beforeSend: function(xhr) { 
          xhr.setRequestHeader("Authorization", "Basic " + btoa("admin:geoserver")); 
        },
        type: 'PUT',
        // dataType: 'json',
        contentType: 'text/plain',
        processData: false,
        data: formData,
        success: function (data) {
          alert(data);
        },
        error: function(error){
          alert("Cannot perform action :"+JSON.stringify(error));
        }
    });

  });