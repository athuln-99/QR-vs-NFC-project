package com.example.myapplication

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.info_blocks.view.*

/**
 * Class is to create a group of info boxes for the view, after reading information from a nfc card
 */
class NFCResponseViewUtilities(
        private val infoBlocks: MutableList<InfoBlock>
) : RecyclerView.Adapter<NFCResponseViewUtilities.InfoViewHolder>() {

        /**
         * Inner class that is a subclass of the RecyclerView
         */
        class InfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        /**
         * Method when the view holder is created
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
                return InfoViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                                R.layout.info_blocks,
                                parent,
                                false
                        )
                )
        }

        /**
         * Adding an info block
         */
        fun addInfoBlock(infoBlock: InfoBlock) {
                infoBlocks.add(infoBlock)
                notifyItemInserted(infoBlocks.size - 1)
        }


        /**
         * Deleting an info block
         */
        fun deleteInfoBlocks() {
                infoBlocks.removeAll { allInfo ->
                        allInfo.isChecked
                }
                notifyDataSetChanged()
        }

        /**
         * Method to strike the selected text on the view
         */
        private fun toggleStrikeThrough(tvInfoTitle: TextView, isChecked: Boolean) {
                if(isChecked) {
                        tvInfoTitle.paintFlags = tvInfoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
                } else {
                        tvInfoTitle.paintFlags = tvInfoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
                }
        }

        /**
         * Method to display the info boxes, with the check box on the view
         */
        override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
                val currentBlock = infoBlocks[position]
                holder.itemView.apply {
                        tvInfoTitle.text = currentBlock.title
                        cbDone.isChecked = currentBlock.isChecked
                        toggleStrikeThrough(tvInfoTitle, currentBlock.isChecked)

                        //A listener for the check boxes
                        cbDone.setOnCheckedChangeListener { _, isChecked ->
                                toggleStrikeThrough(tvInfoTitle, isChecked)
                                currentBlock.isChecked = !currentBlock.isChecked
                        }
                }
        }

        /**
         * Method to get the count of items/ info blocks
         */
        override fun getItemCount(): Int {
                return infoBlocks.size
        }
}

















