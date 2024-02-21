eval(resources[0])

let selectedEntryIndexes = params["selector"]
let target = params["target"]

if (debug) {
  console.log(`Input entries: ${entries.length}`)
  console.log(`Selected entries: ${selectedEntryIndexes.length}`)
}

for (let index of selectedEntryIndexes) {
  let entry = entries[index]
  
  if (debug) {
    console.log(`From: ${entry.name}`)
  }
  
  if (target === "hiragana") {
    entry.name = wanakana.toHiragana(entry.name)
  } else {
    entry.name = wanakana.toRomaji(entry.name)
  }

  if (debug) {
    console.log(`To: ${entry.name}`)
  }
}
