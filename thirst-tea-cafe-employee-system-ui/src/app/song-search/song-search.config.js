export default function songSearchConfig($stateProvider) {
  'ngInject';

  $stateProvider
    .state('song-search', {
      url: '/songs',
      component: 'songSearch'
    });
}
