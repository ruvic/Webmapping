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
                        <select id="attrSelect" name="attrSelect">
                            <option value="name">name</option>
                            <option value="saab">Saab</option>
                            <option value="mercedes">Mercedes</option>
                        </select>
                        <select id="opSelect" name="opSelect">
                            <option value="=">=</option>
                            <option value="saab">Saab</option>
                            <option value="mercedes">Mercedes</option>
                        </select>
                        <input type="text" name="value" id="value"/>
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
    </script>
    <script src="assets/ol3/js/ol-debug.js"></script>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/map.js"></script>
</html>