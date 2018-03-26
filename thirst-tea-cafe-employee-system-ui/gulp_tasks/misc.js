const path = require('path');

const gulp = require('gulp');
const del = require('del');
const filter = require('gulp-filter');
const jsdoc = require('gulp-jsdoc3');

const conf = require('../conf/gulp.conf');

gulp.task('clean', clean);
gulp.task('other', other);
gulp.task('doc', doc);

function clean() {
  return del([conf.paths.dist, conf.paths.tmp]);
}

function other() {
  const fileFilter = filter(file => file.stat.isFile());

  return gulp.src([
    path.join(conf.paths.src, '/**/*'),
    path.join(`!${conf.paths.src}`, '/**/*.{scss,js,html}')
  ])
    .pipe(fileFilter)
    .pipe(gulp.dest(conf.paths.dist));
}

function doc(cb) {
  gulp.src(['./src/**/*.js'], {read: false})
        .pipe(jsdoc(cb));
}