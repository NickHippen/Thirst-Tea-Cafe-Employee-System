export default routesConfig;

function routesConfig($stateProvider, $urlRouterProvider, $locationProvider) {
  'ngInject';
  $locationProvider.html5Mode(false).hashPrefix('!');
  $urlRouterProvider.otherwise('/');

  $stateProvider
    .state('home', {
      url: '/',
      component: 'app'
    });
}
