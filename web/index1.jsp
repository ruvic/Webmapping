<!DOCTYPE html>
<html lang='en'>
    <head>
        <!-- META SECTION -->
        <title>Webmapping FM</title>            
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />

        <link rel="icon" href="favicon.ico" type="image/x-icon" />
        <!-- END META SECTION -->

        <!-- CSS INCLUDE -->  
        <link rel="stylesheet" href="assets/ol3/css/ol.css" type="text/css" />
        <link rel="stylesheet" href="assets/css/samples.css" type="text/css" />
        <link rel="stylesheet" type="text/css" id="theme" href="assets/css/theme-default.css"/>
              
        <!-- EOF CSS INCLUDE -->  
        
    </head>
    <body>
        <div class="se-pre-con"></div>
        <!-- START PAGE CONTAINER -->
        <div class="page-container">
            
            <!-- START PAGE SIDEBAR -->
            <div class="page-sidebar" id="sidebar">
                
            </div>
            <!-- END PAGE SIDEBAR -->
            
            <!-- PAGE CONTENT -->
            <div class="page-content">
                <div id="sup_nav"></div>
                <!-- START X-NAVIGATION VERTICAL -->
                <ul class="x-navigation x-navigation-horizontal x-navigation-panel">
                        <!-- TOGGLE NAVIGATION -->
                        <li class="xn-icon-button">
                            <a href="#" class="x-navigation-minimize"><span class="fa fa-dedent"></span></a>
                        </li>
                        <!-- START X-NAVIGATION -->
                        <li class="xn-logo">
                            <a href="index1.jsp.html">Cameroon's<span class="fa fa-eye"></span></a>
                            <a href="#" class=" x-navigation-control"></a>
                        </li>   
                
                    
                    <!-- END X-NAVIGATION -->
                        <!-- END TOGGLE NAVIGATION -->
                        <!-- SEARCH -->
                        <li class="xn-icon-button">
                            <div id="titre"></div>
                        </li>
                        <!-- END SEARCH -->
                        
                    </ul>
                    <!-- END X-NAVIGATION VERTICAL -->                      
                <div id="page_content">
                
                <!-- PAGE CONTENT WRAPPER -->
                <div class="page-content-wrap">
                                    
                     <div class=row>
                        <div class="col-md-7">
                            <!-- START jVectorMap Europe -->
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Carte</h3>

                                </div>
                                <div class="panel-body panel-body-map">
                                    <div id="map" class= "map" style="width: 100%; height: 500px;"></div>
                                </div>
                            </div>
                            <!-- END jVectorMap Europe -->
                        </div>
                        <div class="col-md-5">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><span class="fa fa-cogs"></span> Couches</h3>                                          
                                </div>
                                <div class="panel-body">
                                    <form action="#" role="form" class="form-horizontal" id="list-couche">
                                                                            
                                    </form>
                                </div>
                            </div>
                        </div>
                        
                    </div>
                    <div class= "row" >
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Requêtes</h3>
                            </div>
                            <div class="panel-body">
                                <form role="form" class="form-group">
                                    <div class="col-md-6">

                                        <!-- LINKED LIST GROUP-->
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h3 class="panel-title">Sélectionner une couche</h3>
                                            </div>
                                            <div class="panel-body">
                                                    <div class="form-group" style="padding-bottom:30px;">
                                                            <label class="col-md-3 control-label">Couche</label>
                                                            <div class="col-md-9">                            
                                                                <select class="form-control select" data-live-search="true" id="listCoucheSel" name="listCoucheSel">
                                                                    
                                                                </select>
                                                            </div>
                                                     </div>
                                                <div class="list-group border-bottom" id="listAttributSel" >
                                                    
                                                    
                                                </div>                              
                                            </div>
                                        </div>
                                        <!-- END LINKED LIST GROUP-->                        
            
                                    </div>
                                    <div class="col-md-6">

                                            <!-- LINKED LIST GROUP-->
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h3 class="panel-title">Options</h3>
                                                </div>
                                                <div class="panel-body">
                                                        <div class="form-group">
                                                                <label class="col-md-3 control-label">Requête</label>
                                                                <div class="col-md-9">                                            
                                                                    <div class="input-group">
                                                                        <span class="input-group-addon"><span class="fa fa-pencil"></span></span>
                                                                        <input type="text" class="form-control" name="cqlFilter" id="cqlFilter"/>
                                                                    </div>                                            
                                                                    <span class="help-block">Rédigez votre requête ici</span>
                                                                </div>
                                                        </div>  
                                                        <div class="row" style="padding-top: 50px;">
                                                                    <div class="col-md-4"></div>
                                                                    <div class="col-md-4">
                                                                        <div class="row" style="padding-left: 30px;">
                                                                             <div class="col-md-4"><button type="button" class="btn btn-default btn-rounded" id="updateFilter" style="padding-right: -15px">Filtrer</button></div>
                                                                             <div class="col-md-4"></div>
                                                                             <div class="col-md-4"><button type="button" class="btn btn-default btn-rounded" id="resetFilter">Refresh</button></div>
                                                                        </div>
                                                                       
                                                                    </div>
                                                                    <div class="col-md-4"></div>
                                                        </div> 
                                                        <div class="form-group" style="padding-top:30px;">
                                                                <label class="col-md-3 control-label">Charger une couche</label>
                                                                <form id="dataForm" action="/webmapping/map" method="post" enctype="multipart/form-data">
                                                                    <div class="col-md-5">   

                                                                        <input name="file" type="file" class="fileinput btn-primary"  title="Importer" submit/>
                                                                        <span class="help-block">Charger un nouveau shapefile</span>


                                                                    </div>
                                                                    <div class="col-md-4">                                                                                                                                        
                                                                        <button type="submit" class="btn btn-primary">Charger</button>
                                                                    </div>
                                                                </form>     
                                                        </div>                       
                                                </div>
                                            </div>
                                            <!-- END LINKED LIST GROUP-->                        
                
                                        </div>
                                </form>
                            </div>
                        </div>
                    </div>
               
                   
                        
                </div>
                    <!-- END PAGE CONTENT WRAPPER --> 
                                    
            </div>            
            <!-- END PAGE CONTENT -->
        </div>
        <!-- END PAGE CONTAINER -->


       

        <!-- Modal de l'aide -->

        

        <!-- START PRELOADS -->
        <audio id="audio-alert" src="audio/alert.mp3" preload="auto"></audio>
        <audio id="audio-fail" src="audio/fail.mp3" preload="auto"></audio>
        <!-- END PRELOADS -->  
        
        </div> 
        </body>
    <!-- START SCRIPTS -->
        <!-- START PLUGINS -->
        <script>
            let layersString = ${layers};
            let attributesString = ${attributes};
        </script>
        <script type="text/javascript" src="assets/js/plugins/jquery/jquery.min.js"></script>
        <script src="assets/ol3/js/ol-debug.js"></script>
        <script src="assets/js/map.js"></script>
        
        <script type="text/javascript" src="assets/js/plugins/jquery/jquery-ui.min.js"></script>
        <script type="text/javascript" src="assets/js/plugins/bootstrap/bootstrap.min.js"></script>        
        <!-- END PLUGINS -->
        <!-- START THIS PAGE PLUGINS-->        
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>

        <script type='text/javascript' src='assets/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js'></script>
        <script type='text/javascript' src='assets/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js'></script>
        <script type='text/javascript' src='assets/js/plugins/jvectormap/jquery-jvectormap-europe-mill-en.js'></script>
        <script type='text/javascript' src='assets/js/plugins/jvectormap/jquery-jvectormap-us-aea-en.js'></script>
        <script type="text/javascript" src="assets/js/plugins/bootstrap/bootstrap-select.js"></script>
        <script type='text/javascript' src='assets/js/plugins/icheck/icheck.min.js'></script>
        <script type="text/javascript" src="assets/js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js"></script>       
        <!-- END THIS PAGE PLUGINS-->        
        <script type="text/javascript" src="assets/js/plugins/bootstrap/bootstrap-file-input.js"></script>
        <script type="text/javascript" src="assets/js/plugins/bootstrap/bootstrap-select.js"></script>
        <script type="text/javascript" src="assets/js/plugins/tagsinput/jquery.tagsinput.min.js"></script>
        <!-- START TEMPLATE -->
        <script type="text/javascript" src="assets/js/settings.js"></script>
        
        <script type="text/javascript" src="assets/js/plugins.js"></script>        
        <script type="text/javascript" src="assets/js/actions.js"></script>
        <script type="text/javascript" src="assets/js/demo_maps.js"></script>
        <script type="text/javascript" src="assets/js/couches.js"></script>
        <script type="text/javascript" src="assets/js/modernizr.js"></script>
        
        <!-- END TEMPLATE -->
    <!-- END SCRIPTS -->          
    

</html>