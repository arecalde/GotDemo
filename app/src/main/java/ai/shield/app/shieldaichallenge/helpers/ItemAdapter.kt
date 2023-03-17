package ai.shield.app.shieldaichallenge.helpers

import ai.shield.app.shieldaichallenge.R
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView


class ItemAdapter<T, B : ViewDataBinding>(private val items: List<T>, private val lifecycleOwner: LifecycleOwner, private val layout: Int, private val setDatabindingItem: (T, B) -> Unit) : RecyclerView.Adapter<MyViewHolder<T, B>>() {
    val selectedItem = MutableLiveData<Int>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder<T, B> {
        val itemBinding = DataBindingUtil.inflate<B>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return MyViewHolder(itemBinding, lifecycleOwner, setDatabindingItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder<T, B>, position: Int) {
        val item = items[position]
        holder.bind(item)

        val view = holder.itemView

        view.setOnClickListener {
            notifyItemChanged(selectedItem.value ?: 0)
            selectedItem.value = holder.adapterPosition
            notifyItemChanged(holder.adapterPosition)

        }

        if (selectedItem.value == holder.adapterPosition) {
            view.findViewById<LinearLayout>(R.id.item_layout).setBackgroundColor(ContextCompat.getColor(view.context, R.color.shield_primary_blue))
        } else {
            val typedValue = TypedValue()
            view.context.theme.resolveAttribute(androidx.appcompat.R.attr.selectableItemBackground, typedValue, true)
            view.findViewById<LinearLayout>(R.id.item_layout).setBackgroundResource(typedValue.resourceId)
        }

    }

    override fun getItemCount() = items.size
}

class MyViewHolder<T, B: ViewDataBinding>(
    private val binding: B,
    private val lifecycleOwner: LifecycleOwner,
    private val setDatabindingItem: (T, B) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T) {
        binding.lifecycleOwner = lifecycleOwner
        setDatabindingItem(item, binding)
        binding.executePendingBindings()
    }
}