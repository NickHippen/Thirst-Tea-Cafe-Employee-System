export default function songViewConfig($stateProvider) {
  'ngInject';

  $stateProvider
    .state('song-view', {
      url: '/song/:songId',
      component: 'songView'
    });
}
