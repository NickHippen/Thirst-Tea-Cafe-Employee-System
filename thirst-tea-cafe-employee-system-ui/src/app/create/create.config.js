export default function createConfig($stateProvider) {
  'ngInject';

  $stateProvider
    .state('create', {
      url: '/create',
      component: 'create'
    });
}
