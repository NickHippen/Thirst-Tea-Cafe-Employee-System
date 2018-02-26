import createConfig from './create.config';
import create from './create.component';
import CreateService from './create.service';

export default angular.module('tt-employee.create', [])
.config(createConfig)
.component('create', create)
.service('CreateService', CreateService)
.name;
