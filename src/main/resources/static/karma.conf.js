// Karma configuration file, see link for more information
// https://karma-runner.github.io/0.13/config/configuration-file.html

module.exports = function (config) {
  var defaultConfig = {
    basePath: '',
    frameworks: ['jasmine', '@angular/cli'],
    plugins: [
      require('karma-jasmine'),
      require('karma-jasmine-html-reporter'),
      require('karma-chrome-launcher'),
      require('karma-phantomjs-launcher'),
      require('karma-junit-reporter'),
      require('karma-coverage-istanbul-reporter'),
      require('@angular/cli/plugins/karma')
    ],
    client:{
      clearContext: false // leave Jasmine Spec Runner output visible in browser
    },
    files: [
      { pattern: './node_modules/jquery/dist/jquery.min.js', watched: false }, 
      { pattern: './src/test.ts', watched: false },
    ],
    preprocessors: {
      './src/test.ts': ['@angular/cli']
    },
    mime: {
      'text/x-typescript': ['ts','tsx']
    },
    coverageIstanbulReporter: {
      reports: [ 'html', 'lcovonly', 'cobertura'],
      dir: './coverage',
      fixWebpackSourcePaths: true
    },
    angularCli: {
      environment: 'dev'
    },
    reporters: config.angularCli && config.angularCli.codeCoverage
              ? ['progress', 'coverage-istanbul']
              : ['progress', 'kjhtml'],
    autoWatch: true,
    failOnEmptyTestSuite: false,
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    browsers: ['Chrome'],
    singleRun: false
  };

  console.log("ENV= ", process.env.ENV);
  if (process.env.ENV === 'CI' || process.env.ENV == 'STG') {
    defaultConfig.autoWatch = false;
    defaultConfig.singleRun = true;
    defaultConfig.browsers = ['PhantomJS'];
    defaultConfig.reporters.push('junit');
    defaultConfig.junitReporter = {
      outputDir: 'test_results',
      outputFile: 'test-results.xml'
    };
    defaultConfig.angularCli = {
      environment: process.env.ENV.toLowerCase()
    }
    console.log('CONFIG FOR JENKINS EXECUTION', defaultConfig);
  }
  config.set(defaultConfig);
};
