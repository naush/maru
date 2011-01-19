(ns leiningen.unit
  "Run the project's unit tests."
  (:refer-clojure :exclude [test])
  (:use [leiningen.test :only [test]]
        [leiningen.util.ns :only [namespaces-in-dir]]))

(defn- actual-source-path [project]
	(str (:source-path project) (System/getProperty "file.separator") (:name project)))

(defn unit
  [project & tests]
  (let [nses (map str (namespaces-in-dir (actual-source-path project)))
        units (filter #(.endsWith % "unit") nses)]
    (apply (partial test project) units)))
