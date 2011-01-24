(ns maru.common.gtp.command.unit
  (:refer-clojure :exclude [name])
  (:use [maru.common.gtp.core] :reload)
  (:use [maru.common.gtp.command.core] :reload)
  (:use [clojure.test]))

(deftest match-command-match
  (is (= true (match {:name "quit" :count 0} {:name "quit" :count 0}))))

(deftest match-command-unmatch
  (is (= false (match {:name "quit" :count 0} {:name "known_command" :count 1}))))

(deftest validate-command-with-valid
  (is (= true (valid {:name "quit" :count 0}))))

(deftest validate-command-with-invalid
  (is (= false (valid {:name "quit" :count 1}))))

(deftest execute-by-function-name-with-no-args
  (make-version-command #(assoc (first %) :message "0.4"))
  (is (= "= 0.4\n" (:message (execute "version" (list (hash-map :message "")))))))

(deftest execute-by-function-name-with-args
  (make-boardsize-command #(assoc (first %) :message (str "boardsize " (second %))))
  (is (= "= boardsize 1\n" (:message (execute "boardsize" (list (hash-map :message "") "1"))))))

(deftest execute-by-function-name-with-default-implementation
  (is (= "= 2\n" (:message (execute "protocol_version" (list (hash-map :message "")))))))
