(ns threesum.core-test
  (:use [midje.sweet])
  (:require [threesum.core :refer :all]))

(facts "about `squashable-pair?`"
       (fact "first nil is always squashable"
             (squashable-pair? nil :a) => true)
       (fact "second nil is not squashable"
             (squashable-pair? :a nil) => false)
       (fact "1 and 2 can squash"
             (squashable-pair? 1 2) => true
             (squashable-pair? 2 1) => true)
       (fact "pairs of no less than 3 can squash"
             (squashable-pair? 3 3) => true
             (squashable-pair? 6 6) => true)
       (fact "pairs of 1 or 2 can't squash"
             (squashable-pair? 1 1) => false
             (squashable-pair? 2 2) => false)
       (fact "different numbers can't squash"
             (squashable-pair? 3 6) => false
             (squashable-pair? 6 12) => false))

(facts "about `squash-pair`"
       (fact "produces sum"
             (squash-pair 1 2) => 3)
       (fact "replaces nil with number"
             (squash-pair nil 5) => 5)
       (fact "squashes nils together"
             (squash-pair nil nil) => nil))

(facts "about `shift`"
       (fact "squashes first squashable pair of cells"
             (shift [:a :b :c :d] :next #(= [:a :b] [%1 %2]) (constantly :ab)) => [:ab :c :d :next]
             (shift [:a :b :c :d] :next #(= [:b :c] [%1 %2]) (constantly :bc)) => [:a :bc :d :next]
             (shift [:a :a :a :a] :next (constantly true) (constantly :aa)) => [:aa :a :a :next])
       (fact "replaces nil in last cell"
             (shift [:a :b :c nil] :next (constantly false) (constantly nil)) => [:a :b :c :next])
       (fact "does not squash next into last cell"
             (shift [:a :b :c :d] :next #(= [:d :next] [%1 %2]) (constantly nil)) => [:a :b :c :d])
       (fact "does not squash if there are no squashable pairs"
             (shift [:a :b :c :d] :next (constantly false) (constantly nil)) => [:a :b :c :d]))

(facts "about `shiftable-row?`"
       (let [row [:a :b :c]]
         (fact "true when the row is different when squashed"
               (shiftable-row? row (constantly [:other]) nil nil) => true)
         (fact "false when the row is the same when squashed"
               (shiftable-row? row (constantly row) nil nil) => false)))
