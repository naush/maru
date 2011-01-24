(ns maru.common.utility.unit
  (:require [maru.common.game.color.core :as color])
  (:use [maru.common.utility.core] :reload)
  (:use [clojure.test]))

(deftest test-even-terms
  (is (= (list 2 4) (even-terms (list 1 2 3 4 5)))))

(deftest test-split-string-at-pointition-one
  (is (= (list "ab" "c") (split-string-at "abc" 1))))

(deftest test-insert-element-at-pointition-last
  (is (= (list "a" "b" "c") (insert-last (list "a" "b") "c"))))

(deftest test-remove-string-upto-index
  (is (= "bc" (remove-string-upto "abc" 1))))

(deftest convert-letter-to-digit
  (is (= 7 (letter-to-digit (first "H12")))))

(deftest convert-digit-to-letter
  (is (= "H" (digit-to-letter 7))))

(deftest convert-string-to-integer
  (is (= 12 (string-to-digit "12"))))

(deftest convert-digit-to-letter
  (is (= 12 (string-to-digit "12"))))

(deftest convert-string-to-point
  (is (= 140 (point-from-string "H12" 19))))

(deftest convert-pointition-to-string
  (is (= "H12" (string-from-point 140 19))))

(deftest convert-string-to-color
  (is (= color/white (string-to-color "W")))
  (is (= color/white (string-to-color "w")))
  (is (= color/black (string-to-color "B")))
  (is (= color/black (string-to-color "b"))))
