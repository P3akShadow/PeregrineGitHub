if (typeof kotlin === 'undefined') {
  throw new Error("Error loading module 'PeregrineJS'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'PeregrineJS'.");
}
var PeregrineJS = function (_, Kotlin) {
  'use strict';
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var println = Kotlin.kotlin.io.println_s8jyv4$;
  var equals = Kotlin.equals;
  var throwCCE = Kotlin.throwCCE;
  var Regex_init = Kotlin.kotlin.text.Regex_init_61zpoe$;
  var ensureNotNull = Kotlin.ensureNotNull;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var substring = Kotlin.kotlin.text.substring_fc3b62$;
  var toShort = Kotlin.toShort;
  var Unit = Kotlin.kotlin.Unit;
  function UrlColumnMap(url, blockOnPage, columnOnPage) {
    this.url = url;
    this.blockOnPage = blockOnPage;
    this.columnOnPage = columnOnPage;
  }
  UrlColumnMap.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'UrlColumnMap',
    interfaces: []
  };
  function main(args) {
    var tmp$;
    println('hello - im here to sign you into a tiss course');
    if (equals(document.URL, 'https://tiss.tuwien.ac.at/education/course/courseRegistration.xhtml') || equals(document.URL, 'https://tiss.tuwien.ac.at/education/course/groupList.xhtml')) {
      println('clicking');
      var button = Kotlin.isType(tmp$ = document.getElementById('regForm:j_id_2x'), HTMLInputElement) ? tmp$ : throwCCE();
      button.click();
    }
    var url = document.URL;
    var courseNrRegex = Regex_init('courseNr');
    var match = courseNrRegex.find_905azu$(url);
    var numberRange = new IntRange(ensureNotNull(match).range.endInclusive + 2 | 0, match.range.endInclusive + 7 | 0);
    var courseNr = substring(url, numberRange);
    println(courseNr);
    var path = 'chrome-extension://mmojljechnociddmllimabfekkidbpgh/PeregrineJVM/urlColumnMatches/match' + courseNr + '.JSON';
    clickAtPos(path);
  }
  function clickAtPos$lambda(closure$xhr, closure$fileInput) {
    return function (it) {
      var tmp$;
      if (closure$xhr.readyState === toShort(4) && closure$xhr.status === toShort(200)) {
        closure$fileInput.v = closure$xhr.responseText;
        var urlColumnMap = JSON.parse(closure$fileInput.v);
        println(urlColumnMap.blockOnPage);
        println(urlColumnMap.columnOnPage);
        println(document.getElementById('groupContentForm:j_id_4t:' + urlColumnMap.blockOnPage + ':j_id_57:' + urlColumnMap.columnOnPage + ':j_id_9q'));
        var button = Kotlin.isType(tmp$ = document.getElementById('groupContentForm:j_id_4t:' + urlColumnMap.blockOnPage + ':j_id_57:' + urlColumnMap.columnOnPage + ':j_id_9q'), HTMLInputElement) ? tmp$ : throwCCE();
        button.click();
      }
      return Unit;
    };
  }
  function clickAtPos(path) {
    var fileInput = {v: null};
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = clickAtPos$lambda(xhr, fileInput);
    xhr.open('GET', path);
    xhr.send();
  }
  _.UrlColumnMap = UrlColumnMap;
  _.main_kand9s$ = main;
  _.clickAtPos_61zpoe$ = clickAtPos;
  main([]);
  Kotlin.defineModule('PeregrineJS', _);
  return _;
}(typeof PeregrineJS === 'undefined' ? {} : PeregrineJS, kotlin);
