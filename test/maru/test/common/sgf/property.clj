(ns maru.test.common.sgf.property
  (:use [maru.common.sgf.property] :reload)
  (:use [clojure.test]))

(deftest parse-a-property-with-missing-key
  (is (= {} (parse "[garbage]"))))

(deftest parse-a-property-with-missing-value
  (is (= {} (parse "garbage[]"))))

(deftest parse-a-property-with-empty-string
  (is (= {} (parse ""))))

(deftest parse-a-property-with-no-match
  (is (= {} (parse "garbage"))))

(deftest parse-a-property-with-lower-case-key
  (is (= {} (parse "ff[4]"))))

(deftest parse-a-property
  (is (= {:FF "4"} (parse "FF[4]"))))

(deftest parse-multiple-properties
  (is (= {:FF "4" :GM "1"} (parse "FF[4]GM[1]"))))

(deftest parse-multiple-properties-with-noises
  (is (= {:FF "4" :GM "1"} (parse ";FF[4]\nGM[1]"))))

(deftest parse-add-white-property
  (is (= {:MV [{:W "bb"} {:W "cb"} {:W "cc"}]} (parse "AW[bb][cb][cc]"))))

(deftest parse-add-black-property
  (is (= {:MV [{:B "bb"} {:B "cb"} {:B "cc"}]} (parse "AB[bb][cb][cc]"))))

(deftest parse-move-property
  (is (= {:MV [{:B "bb"} {:W "ww"} {:B "bb"}]} (parse "B[bb]W[ww]B[bb]"))))
