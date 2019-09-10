var app = angular.module('trains', ['ngResource', 'ngGrid', 'ui.bootstrap']);
// Create a controller to bind to the grid section.
app.controller('timeTableController', function ($scope, $rootScope, timeTableWSService, trainService) {
    // Initialize required information: sorting, the first page to show and the grid options.
    $scope.trains = {};
    timeTableWSService.subscribe(function (msg) {
        console.log('msg', msg);
        $scope.trains = msg;
    });
    timeTableWSService.connect($rootScope);
    $scope.sortInfo = {fields: ['id'], directions: ['asc']};
    $scope.gridOptions = {
        data: 'trains.list',
        useExternalSorting: true,
        sortInfo: $scope.sortInfo,
        columnDefs: [
            {field: 'trainId', displayName: 'Train #',  width: '10%' },
            {field: 'routeId', displayName: 'Route #'},
            {field: 'routeName', displayName: 'Route Name'},
            {field: 'arrivalTime', displayName: 'Arrival Time'},
        ],
        multiSelect: false,
        selectedItems: []
    };
    // Refresh the grid, calling the appropriate rest method.
    $scope.refreshGrid = function () {
        console.log("refresh");
        trainService.get(function (data) {
            $scope.trains = data;
        })
    };
    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
    $scope.deleteRow = function (row) {
        $rootScope.$broadcast('deletePerson', row.entity.id);
    };
    $scope.refreshGrid();
});
// Service that provides trains operations
app.factory('trainService', function ($resource) {
    console.log("getting resource");
    return $resource('resources/train/list');
});
app.factory('timeTableWSService',
    function () {
        var service = {};
        service.connect = function ($rootScope) {
            if (service.ws) {
                return;
            }
            var ws = new WebSocket("ws://localhost:9280/timetable");
            //TODO: see commented code
            ws.onopen = function () {
                /*                ws.send(JSON.stringify({
                                    type: "auth",
                                    token: $cookies.token
                                }));*/
                console.log("Succeeded to open a connection");
            };
            ws.onerror = function () {
                console.log("Failed to open a connection");
            };
            ws.onmessage = function (message) { //incoming dto from JavaEE @ServerEndpoint
                var obj = JSON.parse(message.data);
                $rootScope.$apply(service.callback(obj));
                //callback is set when you create subscribe method
            };
            service.ws = ws;
        };
        service.subscribe = function (callback) {
            console.log("subscribed");
            service.callback = callback;
        };
        return service;
    }
);