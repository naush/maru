(ns maru.common.sgf.pattern.core)

(def property #"([A-Z]+)\[([^\[]+)\]")
(def node #"\(([^\(]*)(.*)\)")
(def property-value #"\[([^\[]+)\]")
