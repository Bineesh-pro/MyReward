package com.app.rewarddaddy.myreward

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import io.adjoe.sdk.Playtime
import io.adjoe.sdk.PlaytimeException
import io.adjoe.sdk.PlaytimeNotInitializedException
import io.adjoe.sdk.PlaytimeParams

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view =  inflater.inflate(R.layout.fragment_home, container, false)

        val adjoeClick:CardView = view.findViewById(R.id.adjoe_click)
        adjoeClick.setOnClickListener{

            try {
                val playtimeParams = PlaytimeParams.Builder()
                    .setUaNetwork("network")
                    .setUaChannel("channel")
                    .setUaSubPublisherCleartext("SubPublisherCleartext")
                    .setUaSubPublisherEncrypted("SubPublisherEncrypted")
                    .setPlacement("placement")
                    .build()
                val playtimeCatalogIntent = Playtime.getCatalogIntent(view.context, playtimeParams)
                this.startActivity(playtimeCatalogIntent)
            }
            catch(notInitializedException: PlaytimeNotInitializedException) {
                // you have not initialized the Playtime SDK
            }
            catch(exception: PlaytimeException) {
                // the catalog cannot be displayed for some other reason
            }

        }
        return view
    }



}