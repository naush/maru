(ns maru.test.common.game.stone.core
  (:use [maru.common.game.stone.core] :reload)
  (:use [clojure.test]))

(deftest make-stone-with-color
  (let [stone (craft 0 0)]
    (is (= 0 (:color stone)))))

(deftest make-stone-with-color-and-point
  (let [stone (craft 0 0)]
    (is (= 0 (:color stone)))
    (is (= 0 (:point stone)))))
