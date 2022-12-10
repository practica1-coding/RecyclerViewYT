package net.practicalcoding.recyclerviewyt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), MyAdapter.RecyclerViewEvent {
    private val data = createData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.theBestRecyclerViewOnThePlanet)
        recyclerView.adapter = MyAdapter(data, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onItemClick(position: Int) {
        val molecule = data[position]

        Toast.makeText(
            this,
            molecule.name,
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * Uses the repository to collect the raw data and bundles up those values
     * into our Molecule data class, something our adapter knows how to work with
     */
    private fun createData(): List<Molecule> {
        //Get data from the repository
        val names = FakeRepository.moleculeName
        val threeLetterAbbrs = FakeRepository.molecule3LetterAbbr
        val oneLetterAbbrs = FakeRepository.molecule1LetterAbbr
        val structures = FakeRepository.structures

        val moleculeData = ArrayList<Molecule>()
        MoleculeId.values().forEach { moleculeID ->
            //If the Id is in all lists, add molecule to the ArrayList
            if (containsId(moleculeID, names, threeLetterAbbrs, oneLetterAbbrs, structures)) {
                moleculeData.add(
                    Molecule(
                        name = names[moleculeID]!!,
                        threeAbbr = threeLetterAbbrs[moleculeID]!!,
                        oneAbbr = oneLetterAbbrs[moleculeID]!!,
                        structure = structures[moleculeID]!!
                    )
                )
            }
        }

        return moleculeData
    }

    /**
     * Takes in a molecule id and checks if it is contained within all mappings passed
     */
    private fun containsId(moleculeID: MoleculeId, vararg maps: Map<MoleculeId, Any>): Boolean {
        maps.forEach {
            if (moleculeID !in it.keys) { return false }
        }
        return true
    }
}