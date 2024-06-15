package juniar.nicolas.mypokemonapp.util

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GeneralRecyclerViewBindingAdapter<T : Any, VB : ViewBinding>(
    private val diffCallback: DiffCallback,
    private val holderResBinding: (parent: ViewGroup) -> ViewBinding,
    @IdRes val specificResViewId: List<Int>? = null,
    private val onBind: (T, VB, pos: Int) -> Unit,
    private val itemListener: (T, pos: Int, VB) -> Unit = { _, _, _ -> run { } },
    private val specificViewListener: (T, pos: Int, View, posId: Int) -> Unit = { _, _, _, _ -> run { } }
) : RecyclerView.Adapter<GeneralRecyclerViewBindingAdapter.GeneralViewBindingHolder<T, VB>>() {

    private val listData = mutableListOf<T>()
    private lateinit var itemBinding: VB

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GeneralViewBindingHolder<T, VB> {
        itemBinding = holderResBinding.invoke(p0) as VB
        val specificView: ArrayList<View> = ArrayList()
        var view: View?
        specificResViewId?.let {
            it.forEach { resId ->
                view = itemBinding.root.findViewById(resId)
                view?.let { it1 -> specificView.add(it1) }
            }
        }
        return GeneralViewBindingHolder(itemBinding, specificView)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(p0: GeneralViewBindingHolder<T, VB>, p1: Int) {
        p0.bindView(listData[p0.adapterPosition], onBind, itemListener, specificViewListener)
    }

    fun setData(datas: List<T>) {
        calculateDiff(datas)
    }

    fun addData(newDatas: List<T>) {
        val list = ArrayList(this.listData)
        list.addAll(newDatas)
        calculateDiff(list)
    }

    fun clearData() {
        calculateDiff(emptyList())
    }

    private fun calculateDiff(newDatas: List<T>) {
        diffCallback.setList(listData, newDatas)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(listData) {
            clear()
            addAll(newDatas)
        }
        result.dispatchUpdatesTo(this)
    }

    fun getItemAt(pos: Int): T? {
        if (pos > listData.size) {
            return null
        }

        return listData[pos]
    }

    class GeneralViewBindingHolder<T : Any, VB : ViewBinding>(
        private val itemBinding: VB,
        private val specificView: List<View>? = null
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindView(
            item: T,
            onBind: (T, VB, pos: Int) -> Unit,
            itemListener: (T, pos: Int, VB) -> Unit,
            specificViewListener: (T, pos: Int, View, posId: Int) -> Unit
        ) {
            with(itemBinding) {
                onBind.invoke(item, this, adapterPosition)
                root.setOnClickListener {
                    itemListener.invoke(item, adapterPosition, this)
                }
            }
            specificView?.forEachIndexed { index, view ->
                view.setOnClickListener {
                    specificViewListener.invoke(item, adapterPosition, itemView, index)
                }
            }
        }
    }
}