(ns maru.common.sgf.core
  (:require [maru.common.sgf.node :as node])
  (:require [maru.common.sgf.property :as property])
  (:require [clojure.contrib.string :only [partition] :as string]))

(defn parse [sgf]
  (if (empty? sgf) {}
  (let [property-node-stream (node/parse sgf)
        property-stream (first property-node-stream)
        node-stream (node/split (second property-node-stream))]
        (node/create (property/parse property-stream)
                     (parse (first node-stream))
                     (map #(parse %1) (rest node-stream))))))

(defn dump-moves [node]
  (if (empty? node) []
    (clojure.set/union (:MV (:properties node)) (dump-moves (:next node)))))

(defn parse-file [path] (parse (slurp path)))
