package com.emagalha.desafio_api.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.emagalha.desafio_api.dto.FotoUploadResponse;
import com.emagalha.desafio_api.dto.FotoUrlResponse;
import com.emagalha.desafio_api.entity.FotoPessoa;
import com.emagalha.desafio_api.entity.Pessoa;
import com.emagalha.desafio_api.exception.BusinessException;
import com.emagalha.desafio_api.exception.EntityNotFoundException;
import com.emagalha.desafio_api.repository.FotoPessoaRepository;
import com.emagalha.desafio_api.repository.PessoaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FotoService {
    private final MinioService minioService;
    private final FotoPessoaRepository fotoPessoaRepository;
    private final PessoaRepository pessoaRepository;

    @Transactional
    public FotoUploadResponse uploadFoto(Integer pessoaId, MultipartFile file) throws Exception {
        // Validações
        if (file.isEmpty()) {
            throw new BusinessException("Arquivo não pode estar vazio");
        }

        Pessoa pessoa = pessoaRepository.findById(pessoaId)
            .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));

        // Gera nome único para o arquivo
        String objectName = "pessoas/%d/%s-%s".formatted(
            pessoaId,
            UUID.randomUUID(),
            file.getOriginalFilename()
        );

        // Upload para MinIO
        String urlTemporaria = minioService.uploadFoto(file, objectName);

    
        FotoPessoa foto = new FotoPessoa();
        foto.setPessoa(pessoa);
        foto.setBucket(minioService.getBucketName());
        foto.setHash(objectName);
        foto.setData(LocalDate.now());

        FotoPessoa savedFoto = fotoPessoaRepository.save(foto);

        return new FotoUploadResponse(
            savedFoto.getId(),
            file.getOriginalFilename(),
            urlTemporaria,
            LocalDate.now()
        );
    }

    @Transactional(readOnly = true)
    public FotoUrlResponse gerarUrlTempraria(Integer fotoId) throws Exception {
        FotoPessoa foto = fotoPessoaRepository.findById(fotoId)
            .orElseThrow(() -> new EntityNotFoundException("Foto não encontrada"));

        String url = minioService.gerarUrlTemporaria(foto.getHash());
        return new FotoUrlResponse(url, LocalDate.now().plusDays(1));
    }
}