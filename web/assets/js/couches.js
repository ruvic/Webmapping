/*var layersString = {
    "layers" : {
        "layer": [{
            "name":"couche1",
            "href":"lien1"
        },
        {
            "name":"couche2",
            "href":"lien2"
        },
        {
            "name":"couche3",
            "href":"lien3"
        },
        {
            "name":"couche4",
            "href":"lien4"
        },
        {
            "name":"couche5",
            "href":"lien5"
        },
        {
            "name":"couche6",
            "href":"lien6"
        },
        {
            "name":"couche7",
            "href":"lien7"
        },
        {
            "name":"couche8",
            "href":"lien8"
        },
        {
            "name":"couche9",
            "href":"lien9"
        }
    ]
    }
};*/

//Chargement des couches
/*let n=layersString.layers.layer.length;
let couches_lay = document.getElementById('cqlFilter');

let form;
for(let i=0;i<n;i++){
    //Préparation de la checkbox
let c1 = document.createElement('div');
c1.setAttribute('class','col-md-3');
let c2 = document.createElement('label');
c2.setAttribute('class','switch');
let c3 = document.createElement('input');
c3.setAttribute('type','checkbox');
c3.setAttribute('class','switch');
c3.setAttribute('value','1');
c2.appendChild(c3);
c2.appendChild(document.createElement('span'));
c1.appendChild(c2);
    console.log("Ici "+i);
    form = document.createElement('div');
    form.setAttribute('class', 'form-group');
    form.appendChild(c1);
    let a1=document.createElement('div');
    a1.setAttribute('class','col-md-9');
    let nom = document.createElement('span');
    nom.setAttribute('class', 'help-block');
    nom.innerHTML= layersString.layers.layer[i].name;
    a1.appendChild(nom);
    form.appendChild(a1);
    couches_lay.appendChild(form);
}*/

//Chargement des sections 2
//Chargement de la liste des sections
/*let el_s = document.getElementById('couches1');
for(let i=0;i<n;i++){
    //Création de l'élément option
    let newPara = document.createElement('option');
    newPara.text = layersString.layers.layer[i].name;
    //newPara.value = section[i].split(' ').join('-');
    el_s.appendChild(newPara); 
}*/

//Affichage de la map
var cameroonL = new ol.layer.Image({
    source: new ol.source.ImageWMS({
        url:'http://localhost:8080/geoserver/web_map/wms',
        params:{'LAYERS':'web_map:cm_2018-08-01_wgs84_administrative-boundaries_polygons_admin2'},
        serverType: 'geoserver'
    })
});

//paste this code under the head tag or in a separate js file.
	// Wait for window load
	$(window).load(function() {
		// Animate loader off screen
		$(".se-pre-con").fadeOut("slow");;
	});