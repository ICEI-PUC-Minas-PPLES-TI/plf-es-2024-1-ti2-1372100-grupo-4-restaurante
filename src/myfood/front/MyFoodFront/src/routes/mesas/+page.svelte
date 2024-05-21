<script lang="ts">
  import Button from "$lib/components/ui/button/button.svelte";
  import { onMount } from "svelte";
  import {
    getMesas,
    createMesa,
    deleteMesa,
  } from "../../../../../back_novo/src/main/java/com/myfood/myfoodback/services/mesaService";
  import { Diamonds } from "svelte-loading-spinners";
  import {
    ENDPOINT_URL,
    DEPLOY_FRONT_URL,
    API_URL,
    SIZE,
  } from "$lib/constants";
  // import type { PageData } from "./$types";

  // export let data: PageData;

  let cardapioMesa = `${DEPLOY_FRONT_URL}/mesas`;
  let mesas: string | any[] = [];
  let qrLinks: string[] = [];
  let promise = fetch(`${ENDPOINT_URL}/mesas`);

  type Mesa = {
    id: number;
    nomeMesa: string;
  };

  async function criarMesa() {
    const newMesa: Mesa = {
      id: mesas.length + 1,
      nomeMesa: `Mesa ${mesas.length + 1}`,
    };

    try {
      const createdMesa = await createMesa(newMesa);
      console.log("Mesa adicionado com sucesso:", createdMesa);
      mesas = [...mesas, createdMesa];
      //window.location.reload();
    } catch (error) {
      console.error("Erro:", error);
    }
  }

  async function handleDeleteMesa(mesaId) {
    try {
      await deleteMesa(mesaId);
      mesas = mesas.filter((mesa) => mesa.id !== mesaId);
    } catch (error) {
      console.error(error);
    }
  }

  onMount(async () => {
    try {
      mesas = await getMesas();
    } catch (error) {
      console.error(error);
    }
  });

  function gerarQRCode(mesaId: number) {
    return `${API_URL}${cardapioMesa}/${mesaId}${SIZE}`;
  }
</script>

<div class=" ">
  <div class="py-4">
    <div class="mb-5 mt-5 flex flex-col sm:flex-row justify-between">
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
      {#if mesas.length == 0}
        <div class="flex justify-center items-center mt-40">
          <h1 class="text-xl text-center">Nenhuma mesa registrada!</h1>
        </div>
      {:else}
        <div
          class="grid grid-cols-2 gap-5 sm:grid-cols-2 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-4 justify-center items-center"
        >
          {#each mesas as mesa (mesa.id)}
            <div
              class="w-full border shadow-lg p-5 flex flex-col gap-2 rounded-lg"
            >
              <h2 class="text-center text-xl">{mesa.id}</h2>
              <img src={mesa.qrCode} alt="QR Code" /><br />
              <div class="flex gap-2">
                <Button
                  on:click={() => handleDeleteMesa(mesa.id)}
                  variant="buttonDD">Deletar mesa</Button
                >
                <Button variant="buttonDD">
                  <a
                    class="flex justify-end"
                    target="_blank"
                    href={mesa.qrCode}
                    download="qrcode">Download</a
                  >
                </Button>
              </div>
            </div>
          {/each}
        </div>
      {/if}
    {/await}
  </div>
</div>
