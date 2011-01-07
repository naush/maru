(ns maru.test.common.game.info
  (:use [maru.common.game.info] :reload)
  (:use [clojure.test]))

(deftest set-and-get-komi
  (set-komi 6.5)
  (is (= 6.5 komi)))

(deftest set-and-get-size
  (set-size 9)
  (is (= 9 size)))
