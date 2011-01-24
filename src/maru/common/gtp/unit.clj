(ns maru.common.gtp.unit
  (:use [maru.common.gtp.core] :reload)
  (:refer-clojure :exclude [name])
  (:use [clojure.test]))

(deftest get-protocol-version
  (is (= "2" (:message (protocol_version (hash-map :messge ""))))))

(deftest set-and-get-name
  (make-name-command #(assoc (first %) :name "maru"))
  (is (= "maru" (:name (name (hash-map :name "default"))))))

(deftest set-and-get-version
  (make-version-command #(assoc (first %) :version "0.1"))
  (is (= "0.1" (:version (version (hash-map :version "0.0"))))))

(deftest get-commands
  (is (= "protocol_version\nname\nversion\nknown_command\nlist_commands\nquit\nboardsize\nclear_board\nkomi\nplay\ngenmove"
      (:message (list_commands (hash-map :message ""))))))

(deftest given-known-command-returns-true
  (is (= true (:message (known_command (hash-map :message "") "protocol_version")))))

(deftest given-last-known-command-returns-true
  (is (= true (:message (known_command (hash-map :message "") "genmove")))))

(deftest given-unknown-command-returns-false
  (is (= false (:message (known_command (hash-map :message "") "unknown_command")))))

(deftest given-partial-command-returns-false
  (is (= false (:message (known_command (hash-map :message "") "ver")))))

(deftest implement-boardsize-and-return-boardize
  (make-boardsize-command #(str "boardsize " (first %)))
  (is (= "boardsize 1" (boardsize "1"))))

(deftest parse-input-with-no-args
  (make-version-command #(assoc (first %) :message ""))
  (is (= "=\n" (:message (parse "version" (hash-map :message ""))))))

(deftest parse-input-with-arg
  (make-boardsize-command #(hash-map :message (str "boardsize " (second %))))
  (is (= "= boardsize 19\n" (:message (parse "boardsize 19" (hash-map :message ""))))))

(deftest parse-input-with-multiple-args
  (make-play-command #(hash-map :message (str "play " (second %) " " (last %))))
  (is (= "= play B Q16\n" (:message (parse "play B Q16" (hash-map :message ""))))))

(deftest parse-command-with-special-character
  (make-name-command #(hash-map :name "maru"))
  (is (= "= true\n" (:message (parse "known_command name" (hash-map :message ""))))))

(deftest parse-invalid-name
  (is (= "? command not found\n" (:message (parse "suicide" (hash-map :message ""))))))

(deftest parse-unimplemented-command-name
  (is (= "? unimplemented\n" (:message (parse "clear_board" (hash-map :message ""))))))

(deftest parse-command-with-illegal-arguments
  (make-version-command #(hash-map :message "version"))
  (is (= "? command not found\n" (:message (parse "version 1" (hash-map :message ""))))))
