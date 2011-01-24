(ns maru.common.game.log.unit
  (:use [maru.common.game.log.core] :reload)
  (:use [clojure.test]))

(deftest append-one-move-to-log
  (is (= (list 0) (ging () 0))))
