package com.adobe.aem.guides.wknd.core.models.impl;


import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;


import com.adobe.aem.guides.wknd.core.models.BylineModel;
import com.adobe.cq.wcm.core.components.models.Image;


import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;


import org.apache.sling.models.factory.ModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.apache.sling.api.resource.Resource;


import java.util.List;
import com.google.common.collect.ImmutableList;


@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class BylineModelImplTest {


    private final AemContext ctx = new AemContext();


    @Mock
    private Image image;


    @Mock
    private ModelFactory modelFactory;


    @BeforeEach
    public void setUp() {
        ctx.addModelsForClasses(BylineModelImpl.class);

        ctx.load().json("/com/adobe/aem/guides/wknd/core/models/impl/BylineModelImplTest.json", "/content");

        lenient().when(modelFactory.getModelFromWrappedRequest(eq(ctx.request()), any(Resource.class), eq(Image.class)))
                .thenReturn(image);

        ctx.registerService(ModelFactory.class, modelFactory, org.osgi.framework.Constants.SERVICE_RANKING,
                Integer.MAX_VALUE);
    }


    @Test
    public void testGetName() {
        final String expected = "Jane Doe";


        ctx.currentResource("/content/byline");
        BylineModel byline = ctx.request().adaptTo(BylineModel.class);


        String actual = byline.getName();


        assertEquals(expected, actual);
    }


    @Test
    public void testGetOccupations() {
        List<String> expected = new ImmutableList.Builder<String>().add("Blogger").add("Photographer").add("YouTuber")
                .build();


        ctx.currentResource("/content/byline");
        BylineModel byline = ctx.request().adaptTo(BylineModel.class);


        List<String> actual = byline.getOccupations();


        assertEquals(expected, actual);
    }


    @Test
    public void testIsEmpty() {
        ctx.currentResource("/content/empty");


        BylineModel byline = ctx.request().adaptTo(BylineModel.class);


        assertTrue(byline.isEmpty());
    }


    @Test
    public void testIsEmpty_WithoutName() {
        ctx.currentResource("/content/without-name");


        BylineModel byline = ctx.request().adaptTo(BylineModel.class);


        assertTrue(byline.isEmpty());
    }


    @Test
    public void testIsEmpty_WithoutOccupations() {
        ctx.currentResource("/content/without-occupations");


        BylineModel byline = ctx.request().adaptTo(BylineModel.class);


        assertTrue(byline.isEmpty());
    }


    @Test
    public void testIsEmpty_WithoutImage() {
        ctx.currentResource("/content/byline");


        lenient().when(modelFactory.getModelFromWrappedRequest(eq(ctx.request()), any(Resource.class), eq(Image.class)))
                .thenReturn(null);


        BylineModel byline = ctx.request().adaptTo(BylineModel.class);


        assertTrue(byline.isEmpty());
    }


    @Test
    public void testIsEmpty_WithoutImageSrc() {
        ctx.currentResource("/content/byline");


        when(image.getSrc()).thenReturn("");


        BylineModel byline = ctx.request().adaptTo(BylineModel.class);


        assertTrue(byline.isEmpty());
    }


    @Test
    public void testIsNotEmpty() {
        ctx.currentResource("/content/byline");
        when(image.getSrc()).thenReturn("/content/bio.png");


        BylineModel byline = ctx.request().adaptTo(BylineModel.class);


        assertFalse(byline.isEmpty());
    }
}