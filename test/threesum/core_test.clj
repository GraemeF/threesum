(ns threesum.core-test
  (:use [midje.sweet])
  (:require [threesum.core :refer :all]))

(facts "about `squashable?`"
       (fact "nil is always squashable"
             (squashable? nil :a) => true)
       (fact "1 and 2 can squash"
             (squashable? 1 2) => true
             (squashable? 2 1) => true)
       (fact "pairs of no less than 3 can squash"
             (squashable? 3 3) => true
             (squashable? 6 6) => true)
       (fact "pairs of 1 or 2 can't squash"
             (squashable? 1 1) => false
             (squashable? 2 2) => false)
       (fact "different numbers can't squash"
             (squashable? 3 6) => false
             (squashable? 6 12) => false))

(facts "about `squash`"
       (fact "produces sum"
             (squash 1 2) => 3)
       (fact "replaces nil with number"
             (squash nil 5) => 5)
       (fact "squashes nils together"
             (squash nil nil) => nil))

(facts "about `shift`"
       (fact "discards first nil cell when not squashable"
             (shift [nil :a nil nil] #(= [nil :a] [%1 %2]) (constantly :a)) => [:a nil nil nil]
             (shift [nil nil nil :a] (constantly false) (constantly :x)) => [nil nil :a nil]
             (shift [:a :b :c :d] #(= [:a :b] [%1 %2]) (constantly :ab)) => [:ab :c :d nil])
       (fact "squashes first and second cells when squashable"
             (shift [:a :b :c :d] #(= [:a :b] [%1 %2]) (constantly :ab)) => [:ab :c :d nil]))
