package good.damn.editor.vector.fragments.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class VEFragmentAdapter(
    private val fragments: Array<Fragment>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {

    override fun getItemCount() = fragments.size

    override fun createFragment(
        position: Int
    ) = fragments[position]

}