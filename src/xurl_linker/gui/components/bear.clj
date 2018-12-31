(ns xurl-linker.gui.components.bear
  (:require [xurl-linker.gui.main :refer [dimensions colors]]
            [fn-fx.controls :as ui]
            [fn-fx.diff :refer [component defui render should-update?]]))

(defui AddNote
  (render [this {:keys [status-text] :as state}]
    (ui/grid-pane
      :alignment :center
      :min-width (:width dimensions)
      :min-height (:height dimensions)
      :hgap 10
      :vgap 10
      :padding (ui/insets
                 :bottom 25
                 :left 25
                 :right 25
                 :top 25)
      :children [(ui/text
                    :text "Add Bear Note"
                    :font (ui/font :family "Tahoma"
                                   :weight :normal
                                   :size 20)
                    :grid-pane/column-index 0
                    :grid-pane/row-index 0
                    :grid-pane/column-span 2
                    :grid-pane/row-span 1)

                 (ui/label
                   :text "Title"
                   :grid-pane/column-index 0
                   :grid-pane/row-index 1)

                 (ui/text-field
                   :id :title-field
                   :grid-pane/column-index 1
                   :grid-pane/row-index 1)

                 (ui/h-box
                   :spacing 10
                   :alignment :bottom-right
                   :children [(ui/button :text "Add"
                                         :on-action {:event :add-note
                                                     :fn-fx/include {:title-field #{:text}}})]
                   :grid-pane/column-index 1
                   :grid-pane/row-index 4)

                 (ui/text
                   :text status-text
                   :fill (:firebrick colors)
                   :grid-pane/column-index 1
                   :grid-pane/row-index 6)])))
