<!doctype html>
<html>
  <head>
    <title>Hello OpenStreetMap</title>
    <link rel="stylesheet" href="assets/ol3/css/ol.css" type="text/css" />
    <link rel="stylesheet" href="assets/css/samples.css" type="text/css" />
  </head>
  <body>
    <div class="main-container">
      <div id="layer-container">
        <div id="map" class="map"></div>
        <div>
          <p>Liste des couches</p>
          <div id="list-couche">
            <div>
              <label htmlFor="administrative">
                <input type="checkbox" id="layer_0" name="administrative"/>
                <span>Administrative</span>
              </label>
            </div>
            <div>
              <label htmlFor="hydrography">
                <input type="checkbox" id="layer_1" name="hydrography"/>
                <span>Hydrographie</span>
              </label>
            </div>
            <div>
              <label htmlFor="Immeubles">
                <input type="checkbox" id="layer_2" name="Immeubles"/>
                <span>Rails en service</span>
              </label>
            </div>
          </div>
          <div class="form-div">
            <form id="dataForm" method="post" enctype="multipart/form-data">
              <input name="shapeFile" type="file"/>
              <button>Charger le fichier</button>
            </form>
          </div>
        </div>
      </div>
    </div>
    <div id="info"> ${layers}</div>
    <div id="info">&nbsp;</div>
  </body>
  <script>
      let layersString = ${layers};
  </script>
  <script src="assets/ol3/js/ol-debug.js"></script>
  <script src="assets/js/jquery.min.js"></script>
  <script src="assets/js/map_test.js"></script>
  <!-- <script src="assets/js/map.js"></script> -->
</html>