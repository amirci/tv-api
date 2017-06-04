(ns tv-api.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]))

(s/defschema TvShow
  {:name (describe s/Str "Name of the show")
   (s/optional-key :description) (describe s/Str "Summary of what the show is about")
   :origin (describe {:country (s/enum :US :CA) :city s/Str} "Country and city where the show is from")})

(def tv-shows
  [{:name "Seinfeld" 
    :description "A show about nothing" 
    :origin {:country :US :city "New York"}}
   {:name "How I met your mother" 
    :description "Legen... dary!" 
    :origin {:country :US :city "New York"}}
   {:name "Murdoch Misteries" 
    :description "A detective in Toronto 1900" 
    :origin {:country :CA :city "Toronto"}}])

(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Tv-api"
                    :description "Tv shows API demo"}
             :tags [{:name "shows", :description "APIs about TV shows"}]}}}

    (context "/api" []
      :tags ["shows"]

      (GET "/tv-shows" []
        :return {:shows [TvShow]}
        :summary "Returns the list of tv shows"
        (-> (ok {:shows tv-shows})
            (header "Access-Control-Allow-Origin" "*"))))))

