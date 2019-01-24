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
                    <div id="list-couche"></div>
                    <div class="form-div">
                        <form id="dataForm" action="/webmapping/map" method="post" enctype="multipart/form-data">
                            <input name="file" type="file"/>
                            <button>Charger le fichier</button>
                        </form>
                    </div>
                    <div>
                        <select id="listCoucheSel" name="listCoucheSel">
                        </select><br>
                        <select id="listAttributSel" name="listAttributSel">
                        </select>
                        <input type="text" name="cqlFilter" id="cqlFilter"/>
                        <button type="button" id="updateFilter">Update</button>
                        <button type="button" id="resetFilter">Reset</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="info">&nbsp;</div>
    </body>
    <script>
        let layersString = ${layers};
        let attributesString = ${attributes};
    </script>
    <script src="assets/ol3/js/ol-debug.js"></script>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/map.js"></script>
</html>