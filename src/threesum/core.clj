(ns threesum.core)
(use '[clojure.core.match :only (match)])

(def board [[0 0 3 0]
            [3 2 0 0]
            [0 1 3 3]
            [1 0 2 1]])

(defn format-row [row]
  (map #(if (= 0 %)
          "    "
          (format "%04d" %))
       row))

(defn print-board [board]
  (doseq [row board]
    (println (format-row row))))

(defn squashable-pair? [a b]
  (match [a b]
         [0 _] true
         [1 2] true
         [2 1] true
         [1 1] false
         [2 2] false
         :else (= a b)))

(defn squash-pair [a b]
    (+ a b))

(defn first-squashable-pair [row squashable-pair?]
  (if (empty? row)
    0
    (if (squashable-pair? (first row) (second row))
      [(first row) (second row)]
      (recur (rest row) squashable-pair?))))

(defn shift-row [row next squashable-pair? squash-pair]
  (if (empty? (rest row))
    (if (= 0 (first row))
      [next]
      row)
    (if (squashable-pair? (first row) (second row))
      (let [squashed (squash-pair (first row) (second row))
            unsquashed (conj (vec (drop 2 row)) next)]
        (into [squashed] unsquashed))
      (into [(first row)] (shift-row (rest row) next squashable-pair? squash-pair)))))

(defn shiftable-row? [row shift-row squashable-pair? squash-pair]
  (not (= (shift-row row :different squashable-pair? squash-pair)
          row)))

(defn shiftable-board? [board shiftable-row?]
  (not (nil? (some shiftable-row? board))))

(defn shift-board [board shift-row]
  (map shift-row board))

(defn -main []
  (let [sr #(shift-row %1 0 squashable-pair? squash-pair)]
    (println "Before:")
    (print-board board)
    (println "After:")
    (print-board (shift-board board sr))))
