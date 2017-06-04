(ns tv-api.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]))

(s/defschema TvShow
  {:name (describe s/Str "Name of the show")
   (s/optional-key :description) (describe s/Str "Summary of what the show is about")
   :poster (describe s/Str "Link to the poster")
   :origin (describe {:country (s/enum :US :CA) :city s/Str} "Country and city where the show is from")})

(def tv-shows
  [{:name "Seinfeld" 
    :description "A show about nothing" 
    :poster "https://thetvdb.com/banners/_cache/posters/79169-5.jpg"
    :origin {:country :US :city "New York"}}
   {:name "How I met your mother" 
    :description "Legen... dary!" 
    :poster "https://thetvdb.com/banners/_cache/posters/75760-34.jpg"
    :origin {:country :US :city "New York"}}
   {:name "The Murdoch Mysteries" 
    :description "A detective in Toronto 1900" 
    :poster "https://thetvdb.com/banners/_cache/posters/81670-1.jpg"
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

