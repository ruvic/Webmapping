$(function(){
    const format = "image/png";

  const layersGroup = obj.layerGroup.publishables.published;
  let layers;
  layers = layersGroup.map(layer => {
    const layerTile = new ol.layer.Tile({
      name: layer.name.slice(-19),
      source: new ol.source.TileWMS({
        ratio: 1,
        url: "http://localhost:8080/geoserver/cameroon/wms",
        params: {
          FORMAT: format,
          VERSION: "1.1.1",
          TILED: true,
          LAYERS: layer.name,
        //   exceptions: "application/vnd.ogc.se_inimage",
        //   tilesOrigin: 8.38221740722656 + "," + 1.65466582775116
        },
        serverType: "geoserver"
      })
    });
    return layerTile;
  });

  const layerGroup = new ol.layer.Group({
    layers: layers,
    name: "cameroun"
  });

  const projection = new ol.proj.Projection({
    code: "EPSG:4326"
  });

  const map = new ol.Map({
    target: "map",
    layers: [layerGroup],
    view: new ol.View({
      //projection: projection,
      center: ol.proj.fromLonLat([12.3446, 7.3696]),
      zoom: 6.4
    }),
    controls: ol.control.defaults().extend([
      //new ol.control.Attribution(),
      new ol.control.ScaleLine({
        units: "degrees",
        minwidth: 100
      }),
      new ol.control.MousePosition({
        coordinateFormat: function(coordinates) {
          const coord_x = coordinates[0].toFixed(3);
          const coord_y = coordinates[1].toFixed(3);
          return coord_x + ", " + coord_y;
        },
        target: "coordinates"
      }),
      new ol.control.ZoomSlider(),
      new ol.control.OverviewMap({
        collapsible: false
      }),
      new ol.control.FullScreen()
    ]),
    interactions: ol.interaction
      .defaults({
        shiftDragZoom: true
      })
      .extend([
        new ol.interaction.Select({
          layers: [layerGroup]
        }),
        new ol.interaction.DragRotateAndZoom()
      ])
  });

  map.on("singleclick", function(evt) {    
    var view = map.getView();
    var viewResolution = view.getResolution();    
    
    layers.map(layer => {
      var url = layer
        .getSource()
        .getGetFeatureInfoUrl(
          evt.coordinate,
          viewResolution,
          view.getProjection(),
          {
            INFO_FORMAT: "text/html",
            FEATURE_COUNT: 50
          }
        );
      if (url) {
        console.log(url);
        document.getElementById("info").innerHTML +=
          '<iframe seamless src="' + url + '"></iframe>';
      }
    });
  });

  const layerList = $("#list-couche");  
  map.getLayers().forEach(layer => {
    layer.getLayers().forEach((sublayer, j) => {
      const layerId = "layer" + j;
      const content = `<li class="list-group-item no-padding-l-r">
      <div class="checkbox checkbox-square checkbox-primary checkbox-lg">
        <input id="${layerId}" type="checkbox" checked=${sublayer.getVisible()}>
        <label for="${layerId}" style="font-size: medium">${sublayer.get(
        "name"
      )}</label>
      </div>
    </li>`;
      layerList.append(content);
      bindInputs(layerId, sublayer);
    });
  });
});