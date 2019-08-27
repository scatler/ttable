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
            {field: 'trainId', displayName: 'Train #'},
            {field: 'routeId', displayName: 'Route #'},
            {field: 'routeName', displayName: 'Route Name'},
            {field: 'arrivalTime', displayName: 'Arrival Time'},
            {
                field: '',
                width: 30,
                cellTemplate: '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>'
            }
        ],

        multiSelect: false,
        selectedItems: []

    };

    // Refresh the grid, calling the appropriate rest method.
        $scope.refreshGrid = function () {
/*
            var listPersonsArgs = {
                sortFields: $scope.sortInfo.fields[0],
                sortDirections: $scope.sortInfo.directions[0]
            };
*/

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

    // Watch the sortInfo variable. If changes are detected than we need to refresh the grid.
    // This also works for the first page access, since we assign the initial sorting in the initialize section.
    /*    $scope.$watch('sortInfo', function () {
            $scope.trains = {currentPage: 1};
            $scope.refreshGrid();
        }, true);*/

    // Do something when the grid is sorted.
    // The grid throws the ngGridEventSorted that gets picked up here and assigns the sortInfo to the scope.
    // This will allow to watch the sortInfo in the scope for changed and refresh the grid.
    /*    $scope.$on('ngGridEventSorted', function (event, sortInfo) {
            $scope.sortInfo = sortInfo;
        });*/

    // Picks the event broadcasted when a person is saved or deleted to refresh the grid elements with the most
    // updated information.
    /*    $scope.$on('refreshGrid', function () {
            $scope.refreshGrid();
        });*/

    // Picks the event broadcasted when the form is cleared to also clear the grid selection.
    /*    $scope.$on('clear', function () {
            $scope.gridOptions.selectAll(false);
        });*/
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
            var ws = new WebSocket("ws://localhost:8080/javaee7-angular/timetable");

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