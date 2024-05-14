<script lang="ts">
  import Button from "$lib/components/ui/button/button.svelte";
  import { onMount } from "svelte";
  import { Diamonds } from "svelte-loading-spinners";
  // import type { PageData } from "./$types";

  // export let data: PageData;

  let endpoint = "http://localhost:8080";
  let cardapioMesa =
    "https://plf-es-2024-1-ti2-1372100-grupo-4-restaurante-deploy-518euk7ox.vercel.app//mesas";
  let mesas: Mesa[] = [];
  let qrLinks: string[] = [];
  let API_URL = "https://api.qrserver.com/v1/create-qr-code/?data=";
  let SIZE = "&size=150x150";
  let promise = fetch(`${endpoint}/mesas`);

  type Mesa = {
    id: number;
    nomeMesa: string;
  };

  async function criarMesa() {
    const newMesa: Mesa = {
      id: mesas.length + 1,
      nomeMesa: `Mesa ${mesas.length + 1}`,
    };

    fetch(`${endpoint}/mesas`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newMesa),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Mesa adicionado com sucesso:", data);
        window.location.reload();
      })
      .catch((error) => {
        console.error("Erro:", error);
      });
  }

  onMount(async () => {
    const response = await fetch(`${endpoint}/mesas`);
    if (!response.ok) {
      console.error("Erro ao buscar mesas:", response.status);
      return;
    }
    mesas = await response.json();
    qrLinks = mesas.map((mesa) => gerarQRCode(mesa.id));
  });

  function gerarQRCode(mesaId: number) {
    return `${API_URL}${cardapioMesa}/${mesaId}${SIZE}`;
  }
</script>

<div class="p-4 sm:ml-64">
  <div class="py-4">
    <div class="flex justify-between items-center gap-0 pb-5">
      <h1 class="text-center text-4xl font-bold text-secondary">
        Mesas restaurante
      </h1>
      <Button on:click={criarMesa} variant="buttonAdd">Cadastrar mesa</Button>
    </div>
    {#await promise}
      <div class="flex items-center justify-center mt-20">
        <Diamonds size="60" color="#FF3E00" unit="px" duration="1s" />
      </div>
    {:then}
      <div
        class="grid grid-cols-2 gap-5 sm:grid-cols-2 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-4 justify-center items-center"
      >
        {#each mesas as mesa, index (mesa.id)}
          <div
            class="w-full border shadow-lg p-5 flex flex-col gap-2 rounded-lg"
          >
            <h2 class="text-center text-xl">{mesa.nomeMesa}</h2>
            {#await qrLinks}
              <Diamonds size="60" color="#FF3E00" unit="px" duration="1s" />
            {:then}
              <img src={qrLinks[index]} alt="QR Code" /><br />
            {/await}
            <div class="flex gap-2">
              <Button variant="buttonDD">Deletar mesa</Button>
              <Button variant="buttonDD">
                <a
                  class="flex justify-end"
                  target="_blank"
                  href={qrLinks[index]}
                  download="qrcode">Download</a
                >
              </Button>
            </div>
          </div>
        {:else}
          <div class="flex justify-center items-center mt-40">
            <h1 class="text-xl text-center">
              Nenhum produto adicionado ao cardápio!
            </h1>
          </div>
        {/each}
      </div>
    {/await}
  </div>
</div>
